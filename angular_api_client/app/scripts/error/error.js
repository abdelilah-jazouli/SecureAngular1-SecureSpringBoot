'use strict';

angular.module('angularApiClientApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('error', {
                parent: 'site',
                url: '/error',
                data: {
                    authorities: []

                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/error/error.html'
                    }
                }
            })
            .state('accessdenied', {
                parent: 'site',
                url: '/accessdenied',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/error/accessdenied.html'
                    }
                }
            });
    });
