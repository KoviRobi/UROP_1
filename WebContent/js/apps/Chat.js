// vim: set sts=2 ts=2 sw=2 et :
var chatApp = angular.module('chatApp',
  [   'ngRoute'
    , 'chatControllers'
    , 'chatServices'
  ]
);

chatApp.config(
  ['$routeProvider',
    function ($routeProvider)
    {
      $routeProvider
        .when('/Chat',
          {   templateUrl : 'partials/Chat.html'
            , controller  : 'ChatCtrl'
          })
        .when('/Login',
          {   templateUrl : 'partials/Login.html'
            , controller  : 'LoginCtrl'
          })
        .otherwise(
          {
            redirectTo: '/Chat'
          })
        ;
    }
  ]
);
