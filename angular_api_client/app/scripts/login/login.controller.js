/**
 * Created by abdelilah on 04/01/16.
 */
(function() {
  'use strict';


  angular.module('angularApiClientApp')
    .controller('LoginController', ['$scope', '$state', '$timeout', 'Authentification',function ($scope, $state, $timeout, Authentification) {
      $scope.user = {};
      $scope.errors = {};

      $timeout(function () {
        angular.element('[ng-model="username"]').focus();
      });

      /**
       * The response object has these properties:
       data – {string|Object} – The response body transformed with the transform functions.
       status – {number} – HTTP status code of the response.
       headers – {function([headerName])} – Header getter function.
       config – {Object} – The configuration object that was used to generate the request.
       statusText – {string} – HTTP status text of the response.
       @See https://code.angularjs.org/1.4.8/docs/api/ng/service/$http
       * @param response
       */

      $scope.login = function (event) {
        event.preventDefault();
        Authentification.login({username: $scope.username, password: $scope.password})
          .then(function (response) {
            console.log('login succes with status : ' + response.status);
            $scope.authenticationError = false;
            $state.go('home');
      }, function (response) {
        console.log('login error with status : ' + response.status);
        $scope.authenticationError = true;
      }).catch(function () {
              $scope.authenticationError = true;
            });

      };

    }])
    .controller('LogoutController',['$scope','Authentification','$state',function($scope,Authentification,$state){
      $scope.logout = function() {
        Authentification.logout();
        $state.go('login');
      };
    }]);

})();
