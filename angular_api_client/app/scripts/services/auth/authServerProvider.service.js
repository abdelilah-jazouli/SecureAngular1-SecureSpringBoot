'use strict';

angular.module('angularApiClientApp')
    .factory('AuthServerProvider', function loginService($http, localStorageService,API_URL) {
        return {
            login: function(credentials) {
                var data = 'j_username=' + encodeURIComponent(credentials.username) +
                    '&j_password=' + encodeURIComponent(credentials.password) ;
                return $http.post(API_URL+'/api/authentication', data, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (response) {
                    return response;
                });
            },
            logout: function() {
                // logout from the server
                $http.post(API_URL+'/api/logout').success(function (response) {
                    localStorageService.clearAll();
                    // to get a new csrf token call the api
                    $http.get(API_URL+'/api/renwewCSRFtoken');
                    return response;
                });
            },
            getToken: function () {
                var token = localStorageService.get('token');
                return token;
            },
            hasValidToken: function () {
                var token = this.getToken();
                return !!token;
            }
        };
    });
