// vim: set sts=2 ts=2 sw=2 et :
var chatApp = angular.module('chatApp', []);

chatApp.controller('UserListCtrl',
  function ($scope)
  {
    $scope.users =
      [ { 'name' : 'test1' }
      , { 'name' : 'test2' }
      ];
  }
);
