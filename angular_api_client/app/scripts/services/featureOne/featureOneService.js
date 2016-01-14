/**********************************
 * Created by abdelilah on 06/01/16.
 **********************************/
    //IIFE
(function () {
    'use strict';
  angular.module('angularApiClientApp')
    .factory('FeautureOneService', ['$http', 'API_URL', function ($http,API_URL) {
      var service = {};
      service.adminPage = function() {
        return $http.get(API_URL +'/api/admin') ;
      };

        service.publicPage = function() {
          return $http.get(API_URL +'/api/public/hellopublic') ;
        };
      service.contactPage = function(){
        return $http.get(API_URL +'/api/contact') ;
      };

      return service ;
    }]);
})();

