'use strict';

/**
 * @ngdoc overview
 * @name angularApiClientApp
 * @description
 * # angularApiClientApp
 *
 * Main module of the application.
 */
angular
  .module('angularApiClientApp', [
    'LocalStorageModule',
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ui.router',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function($stateProvider, $urlRouterProvider, $httpProvider){

    //The “withCredentials” option is used to have the browser include our
    // credentials (most particularly our JSESSIONID cookie) to our requests.
    // This is disabled by default, so you must enable it for our secure AJAX requests to work
    $httpProvider.defaults.withCredentials = true;

    //enable CSRF
    //The $httpProvider defaults “xsrfCookieName” and “xsrfHeaderName” default properties
    // respectively define the name of the cookie used by the server to send the CSRF token,
    // and the name of the header where the server expects the token to be returned.
    // Under those conditions, AngularJS is supposed to automatically copy the token from the cookie
    // to the header. BUT this automatic cookie-to-header copying mechanism is not applied for
    // cross-domain requests.
    // Tough luck! So far I’ve been unable to find a convincing reason on why it is like this.
    // But as a consequence of that we’ll have to copy the cookie value to the header ourselves,
    // using those two variables merely as a convenient way to specify the cookie and header names.
    //see http://www.codesandnotes.be/2015/09/04/angularjs-web-apps-for-spring-based-rest-services-security-the-client-side/
    $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';

      //Enable cross domain calls
      $httpProvider.defaults.useXDomain = true;
      delete $httpProvider.defaults.headers.common['X-Requested-With'];

    $urlRouterProvider.otherwise('/home');
    $stateProvider.state('site',{
      'abstract': true,
      resolve:{
        authorize:['Authentification',function(Authentification){
                return Authentification.authorize();
        }]
      }
    }) ;

    //$httpProvider.interceptors.push('errorHandlerInterceptor');
      $httpProvider.interceptors.push('csfrInterceptor');
      $httpProvider.interceptors.push('authExpiredInterceptor');


  })
  .run(function($rootScope,Principal,Authentification){

      $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
      $rootScope.toState = toState;
      $rootScope.toStateParams = toStateParams;

      if (Principal.isIdentityResolved()) {
        Authentification.authorize();
      }

    });

    $rootScope.$on('$stateChangeSuccess',  function(event, toState, toParams, fromState, fromParams) {

      // Remember previous state unless we've been redirected to login or we've just
      // reset the state memory after logout. If we're redirected to login, our
      // previousState is already set in the authExpiredInterceptor. If we're going
      // to login directly, we don't want to be sent to some previous state anyway
      if (toState.name !== 'login' && $rootScope.previousStateName) {
        $rootScope.previousStateName = fromState.name;
        $rootScope.previousStateParams = fromParams;
      }

    });
  })
  ;
