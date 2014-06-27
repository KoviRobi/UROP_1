// vim: set sts=2 ts=2 sw=2 et :
var chatControllers = angular.module('chatControllers', [ 'ngCookies' ]);

chatControllers.controller('ChatCtrl',
  [ '$scope', 'Chat', '$interval', '$cookies', '$cookieStore',
    function ($scope, Chat, $interval, $cookies, $cookieStore)
    {
      $cookieStore.remove("lastMessage");

      $scope.users = new Array();
      $scope.messages = new Array();

      $scope.sendMessage = function (message)
      {
        Chat.sendMessage({"message" : message});
      }

      var updateUsers = function ()
      {
        $scope.users = Chat.getUsers();

        // Push new messages to old
        Chat.getMessages(null, null,
          // Anonymous callback function
          function (value, responseHeaders)
          {
            // Using the Array.forEach as AngularJS converts it
            // from an array to a different object
            value.forEach(
              function (message)
              {
                message.time = new Date(message.time).toLocaleString();
                $scope.messages.push(message);
              }
            );
          }
        );
      };

      updateUsers();

      // $interval(updateUsers, 5000);
    }
  ]
);

chatControllers.controller('LoginCtrl',
  [
    function ()
    {
    }
  ]
);
