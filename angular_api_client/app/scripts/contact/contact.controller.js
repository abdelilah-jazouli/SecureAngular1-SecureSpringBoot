/**
 * @ngdoc function
 * @name angularApiClientApp.controller:ContactController
 * @description
 * # ContactController
 * Controller of the angularApiClientApp
 */
(function(){
  'use strict';
  angular.module('angularApiClientApp')
      .controller('ContactController', ['FeautureOneService',function (FeautureOneService) {
        var vm = this ;
        vm.message ='' ;
        FeautureOneService.contactPage().then(function(response){
          vm.message = response.data ;
          vm.success = true ;
        },function(response){
          console.log('Contact error with status : '+ response.status);
          vm.success = false ;


        })
            .catch(function() {
              vm.success = false;

            });

      }]);

})();
