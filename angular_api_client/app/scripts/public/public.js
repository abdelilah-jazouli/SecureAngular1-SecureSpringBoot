/**********************************
 * Created by abdelilah on 04/01/16.
 **********************************/
(function(){
'use strict';

angular.module('angularApiClientApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('public', {
        parent: 'site',
        url: '/public',
        data: {
          authorities: []
        },
        views: {
          'content@': {
            templateUrl: 'scripts/public/public.html',
            controller: 'PublicController as vm'
          }
        }
      });
  });
})();
