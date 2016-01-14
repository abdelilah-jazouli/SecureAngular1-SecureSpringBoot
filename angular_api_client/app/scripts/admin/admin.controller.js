/**
 * @ngdoc function
 * @name angularApiClientApp.controller:AdminController
 * @description
 * # AdminController
 * Controller of the angularApiClientApp
 */
(function(){
  'use strict';
  angular.module('angularApiClientApp')
  .controller('AdminController', ['FeautureOneService',function (FeautureOneService) {
    var vm = this ;
    vm.message ='' ;
       FeautureOneService.adminPage().then(function(response) {
           vm.message = response.data;
               vm.success = true;
       },function(response){
           console.log('AdminController : Admin error with status : '+ response.status);
               vm.success = false;

       }
         )
           .catch(function() {
         vm.success = false;
         vm.authenticationError = true;

       });

  }]);
})();
