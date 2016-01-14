/**********************************
 * Created by abdelilah on 04/01/16.
 **********************************/
(function(){
'use strict';

angular.module('angularApiClientApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('admin', {
        parent: 'site',
        url: '/admin',
        data: {
          authorities: ['ROLE_ADMIN']
        },
        views: {
          'content@': {
            templateUrl: 'scripts/admin/admin.html',
            controller: 'AdminController as vm'
          }
        }
      });
  });
})();
