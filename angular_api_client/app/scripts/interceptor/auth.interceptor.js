/**********************************
 * Created by abdelilah on 05/01/16.
 **********************************/
(function(){
'use strict';

angular.module('angularApiClientApp')
  .factory('authExpiredInterceptor', function ($q,$rootScope, $injector,API_URL) {
      function afterCSRFRenewed(oldResponse) {
        if (getCSRF() !== '') {
          console.log('3 authExpiredInterceptor , retry the old request :' + oldResponse.config.url) ;
          // retry the old request after the new CSRF-TOKEN is obtained
          var $http = $injector.get('$http');
          return $http(oldResponse.config);
        } else {
          // unlikely get here but reject with the old response any way and avoid infinite loop
          return $q.reject(oldResponse);
        }
      }

      function getCSRF() {
        var name = 'CSRF-TOKEN=';
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
          var c = ca[i];
          while (c.charAt(0) === ' ') {c = c.substring(1);}
          if (c.indexOf(name) !== -1) {return  c.substring(name.length, c.length) ;}
        }
        return '';
      }

    return {
      'responseError': function(rejection) {
        if (rejection.data.path && rejection.status) {
          console.log('1 authExpiredInterceptor, response status : ' + rejection.status + ' for ' + rejection.data.path) ;
        }
        
        // If we have an unauthorized request we logout and redirect to the login page
        //we also store destination state with parameters
        if (rejection.status === 401){

          var Authentification = $injector.get('Authentification');
          var $state = $injector.get('$state');

          var to = $rootScope.toState;
          var params = $rootScope.toStateParams;

          Authentification.logout() ;
        $rootScope.previousStateName = to;
        $rootScope.previousStateNameParams = params;
        $state.go('login');


        }
          else if (rejection.status === 403  && getCSRF() === '') {
          console.log('2 authExpiredInterceptor , CSRF token expired, then try to get a new CSRF token and retry the old request : '+ rejection.status) ;
          // If the CSRF token expired or not set , then try to get a new CSRF token and retry the old request
          var $http = $injector.get('$http');
          //return $http.get(API_URL+'/api/renwewCSRFtoken').finally(function() { return afterCSRFRenewed(rejection); });

           return $http.get(API_URL+'/api/renwewCSRFtoken').then(function() { return afterCSRFRenewed(rejection); });

        }

        return $q.reject(rejection);

      }
    };


  });

})();
