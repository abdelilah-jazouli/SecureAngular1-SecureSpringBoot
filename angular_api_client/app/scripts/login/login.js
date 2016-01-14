/**********************************
 * Created by abdelilah on 04/01/16.
 **********************************/
(function() {
  'use strict';

  angular.module('angularApiClientApp')
    .config(function ($stateProvider) {
      $stateProvider
        .state('login', {
          parent: 'site',
          url: '/login',
          data: {
            authorities: []
          },
          views: {
            'content@': {
              templateUrl: 'scripts/login/login.html',
              controller: 'LoginController'
            }
          }
        });
//        .state('logout',{
//          parent:'site',
//          url:'/logout',
//          views :{
//            'content@' :{
//              template:'',
//              controller : 'LogoutController'
//            }
//          }
//        });
    });
})();


