/**********************************
 * Created by abdelilah on 09/01/16.
 **********************************/
    //IIFE
(function () {
    'use strict';
    angular.module('angularApiClientApp')

        .factory('csfrInterceptor', ['$log', 'API_URL', function($log,API_URL) {

            //see http://stackoverflow.com/questions/25173132/angularjs-is-not-sending-x-xsrf-token
            function readCookie(name) {
                var nameEQ = name + '=';
                var ca = document.cookie.split(';');
                for(var i=0;i < ca.length;i++) {
                    var c = ca[i];
                    while (c.charAt(0)===' ') {c = c.substring(1,c.length); }
                    if (c.indexOf(nameEQ) === 0) {return c.substring(nameEQ.length,c.length);}
                }
                return null;
            }

            return  {
                request: function (config) {

                    //Do nothing if the URL does not match the API call
                    if (config.url.indexOf(API_URL) < 0 ) {return config ;}
                    //Do nothing if the URL match the renew CSRF cookie call
                    if (config.url.indexOf(API_URL+'/api/renwewCSRFtoken') >= 0 ) {return config ;}


                    var token = readCookie('CSRF-TOKEN') ;

                        if (token) {
                            $log.info('1 csfrInterceptor : Adding X-CSRF-TOKEN header to request ' + config.url + ' with token :' + token);
                            config.headers['X-CSRF-TOKEN'] = readCookie('CSRF-TOKEN');
                        }
                    else {
                        $log.info('--> 1 csfrInterceptor  : CSRF-TOKEN IS EMPTY');
                            $log.info('--> 2 csfrInterceptor   url : ' + config.url);
                            $log.info('--> 3 csfrInterceptor   method : ' + config.method);
                            $log.info('--> 4 csfrInterceptor   xsrfHeaderName : ' + config.xsrfHeaderName);
                            $log.info('--> 5 csfrInterceptor   config.xsrfCookieName : ' + config.xsrfCookieName);
                            $log.info('--> 6 csfrInterceptor   withCredentials : ' + config.withCredentials);


                    }


                    return config ;
                }
            } ;
}]);

})();
 
