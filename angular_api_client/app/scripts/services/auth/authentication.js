(function() {

  'use strict';
  angular.module('angularApiClientApp')
    .factory('Authentification', ['$rootScope','$http','$state','$q','AuthServerProvider','Principal',function ($rootScope,$http,$state,$q,AuthServerProvider,Principal) {
      var service = {};

        //login to  backend server
        service.login = function (credentials,callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            AuthServerProvider.login(credentials).then(function (data) {
                // retrieve the logged account information
                Principal.identity(true).then(function(account) {
                    // After the login  do her somme thing like setting user preferred language ...

                    deferred.resolve(data);
                });
                return cb();
            }).catch(function (err) {
                this.logout();
                deferred.reject(err);
                return cb(err);
            }.bind(this));

            return deferred.promise;

      };

      //logout from  backend server
      service.logout = function () {
          AuthServerProvider.logout();
          Principal.authenticate(null);
          // Reset state memory
          $rootScope.previousStateName = undefined;
          $rootScope.previousStateNameParams = undefined;

      };

      service.authorize = function(force) {
              return Principal.identity(force)
                  .then(function() {
                      var isAuthenticated = Principal.isAuthenticated();

                      // an authenticated user can't access to login
                      if (isAuthenticated &&  $rootScope.toState.name === 'login') {
                          $state.go('home');
                      }

                      if ($rootScope.toState.data.authorities && $rootScope.toState.data.authorities.length > 0 && !Principal.hasAnyAuthority($rootScope.toState.data.authorities)) {
                          if (isAuthenticated) {
                              // user is signed in but not authorized for desired state
                              $state.go('accessdenied');
                          }
                          else {
                              // user is not authenticated. stow the state they wanted before you
                              // send them to the signin state, so you can return them when you're done
                              $rootScope.previousStateName = $rootScope.toState;
                              $rootScope.previousStateNameParams = $rootScope.toStateParams;

                              // now, send them to the signin state so they can log in
                              $state.go('login');
                          }
                      }
                  });
          };

      return service;
    }]);
})();
