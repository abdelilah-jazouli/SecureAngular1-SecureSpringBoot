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
  .controller('PublicController', ['FeautureOneService',function (FeautureOneService) {
    var vm = this ;
        FeautureOneService.publicPage().then(function(response){
          vm.message = response.data ;
            vm.success = true;
        },function(response){
            vm.success = false;

        }).catch(function() {
          vm.success = false;

        });

  }]);
})();
