/**********************************
 * Created by abdelilah on 04/01/16.
 **********************************/
(function() {
  'use strict';

  angular.module('angularApiClientApp')
    .config(function ($stateProvider) {
      $stateProvider
        .state('home', {
          parent: 'site',
          url: '/home',
          data: {
            authorities: []
          },
          views: {
            'content@': {
              templateUrl: 'scripts/home/home.html',
              controller: 'HomeController'
            }
          }
        });
    });
})();

