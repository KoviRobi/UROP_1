// vim: set sts=2 ts=2 sw=2 et :
var chatApp = angular.module('chatApp', []);

chatApp.controller('UserListCtrl',
  [ '$scope', '$http', '$interval',
    function ($scope, $http, $interval)
    {

      var updateUsers = function ()
      {
        $http.get('js/test-users.json').success(
          function (data)
          {
            $scope.users = data;
          }
        );
      };

      updateUsers();

      $interval(updateUsers, 50000);
    }
  ]
);

chatApp.controller('MessageHistoryCtrl',
  [ '$scope', '$http', '$interval',
    function ($scope, $http, $interval)
    {
      var updateMessages = function ()
      {
        $http.get('js/test-messages.json').success(
          function (data)
          {
            $scope.messages = data;
          }
        );
      };

      updateMessages();

      $interval(updateMessages, 50000);
    }
  ]
);
