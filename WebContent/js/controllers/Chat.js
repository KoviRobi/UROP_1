// vim: set sts=2 ts=2 sw=2 et :
var chatControllers = angular.module('chatControllers', [ 'ngCookies' ]);

function XHRMessage(value, responseHeaders)
{
  alert((value.message == undefined)
        ?"Server forgot to put message, anyway, success!"
        :value.message);
}

function XHRError(httpResponse)
{
  console.log(httpResponse);
  if (httpResponse.data != undefined
   && httpResponse.data.error != undefined)
    alert (httpResponse.data.error);
  else if (httpResponse.status != undefined)
    alert ("Got status of "
         + httpResponse.status
         + ", which is not a success. Sorry, I don't know more.");
  else alert ("Something went wrong and I don't know anything about it.\n"
            + "Please email the administrator with a small message, "
            + "containing the current time (don't forget to include your timezone.");
}

chatControllers.controller('ChatCtrl',
  [ '$scope', 'Chat', '$interval', '$cookies', '$cookieStore',
    function ($scope, Chat, $interval, $cookies, $cookieStore)
    {
      $cookieStore.remove("lastMessage");

      $scope.messages = new Array();

      $scope.sendMessage = function (message)
      {
        Chat.sendMessage(null, {"message" : message},
          function ()
          {
            $scope.chatMessageTextarea = "";
          }, XHRError);
      }

      var update = function ()
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

      update();

      $interval(update, 5000);
    }
  ]
);

chatControllers.controller('LoginCtrl',
  [ '$scope', 'User', '$location',
    function ($scope, User, $location)
    {

      $scope.login   = function (username, password)
      {
        User.login(null, {'username' : username, 'password' : password},
          function (value, responseHeaders) // Success
          {
            $location.path("/Chat");
          }, XHRError);
      };

      $scope.addUser = function (username, password)
      {
        User.addUser(null, {'username' : username, 'password' : password}, XHRMessage, XHRError);
      };

      $scope.delUser = function (username, password)
      {
        User.delUser(null, {'username' : username, 'password' : password}, XHRMessage, XHRError);
      };
    }
  ]
);
