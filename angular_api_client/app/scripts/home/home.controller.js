/**
 * @ngdoc function
 * @name angularApiClientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the angularApiClientApp
 */
(function(){
  'use strict';

angular.module('angularApiClientApp')
  .controller('HomeController', function () {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
})();
