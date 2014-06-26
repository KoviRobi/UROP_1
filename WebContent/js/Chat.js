// vim: set sts=2 ts=2 sw=2 et :
var chatApp = angular.module('chatApp', []);

chatApp.controller('UserListCtrl',
  [ '$scope', '$http',
    function ($scope, $http)
    {
      $http.get('js/test-users.json').success(
        function (data)
        {
          $scope.users = data;
        }
      );
    }
  ]
);

chatApp.controller('MessageHistoryCtrl',
  [ '$scope', '$http',
    function ($scope, $http)
    {
      $http.get('js/test-messages.json').success(
        function (data)
        {
          $scope.messages = data;
        }
      );
    }
  ]
);
