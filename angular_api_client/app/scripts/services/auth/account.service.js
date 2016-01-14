'use strict';

angular.module('angularApiClientApp')
    .factory('Account', function Account($resource,API_URL) {
        return $resource(API_URL+'/api/account', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
        });
    });
