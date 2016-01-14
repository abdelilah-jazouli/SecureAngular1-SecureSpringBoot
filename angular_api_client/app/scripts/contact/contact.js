/**********************************
 * Created by abdelilah on 04/01/16.
 **********************************/
(function(){
'use strict';

angular.module('angularApiClientApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('contact', {
        parent: 'site',
        url: '/contact',
        data: {
          authorities: ['ROLE_ADMIN','ROLE_USER']
        },
        views: {
          'content@': {
            templateUrl: 'scripts/contact/contact.html',
            controller: 'ContactController as vm'
          }
        }
      });
  });
})();
