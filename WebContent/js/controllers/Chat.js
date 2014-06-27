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

      $interval(updateUsers, 5000);
    }
  ]
);

chatControllers.controller('LoginCtrl',
  [ '$scope', 'User', '$location',
    function ($scope, User, $location)
    {
      var XHRMessage = function (value, responseHeaders)
      {
        alert(value.message);
      }

      var XHRError = function (httpResponse)
      {
        alert(httpResponse.data.error);
      }

      $scope.login   = function (username, password)
      {
        User.login(null, {'username' : username, 'password' : password},
          function (value, responseHeaders) // Success
          {
            console.log(value);
            console.log(responseHeaders);
          }, XHRError);
      };

      $scope.addUser = function (username, password)
      {
        User.addUser(null, {'username' : username, 'password' : password}, XHRMessage, XHRError);
      };

      $scope.delUser = function (username)
      {
        User.delUser(null, {'username' : username}, XHRMessage, XHRError);
      };
    }
  ]
);
