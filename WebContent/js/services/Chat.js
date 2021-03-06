// vim: set sts=2 ts=2 sw=2 et :
var chatServices = angular.module('chatServices', [ 'ngResource' ]);

chatServices.factory('Chat',
  [ '$resource',
    function ($resource)
    {
      return $resource('rest/Chat/:action',
        {},
        {   getMessages : {method : 'GET', params  : {action : 'GetMessages'}, isArray : true}
          , getUsers    : {method : 'GET', params  : {action : 'GetUsers'},    isArray : true}
          , sendMessage : {method : 'POST', params : {action : 'SendMessage'}}
        }
      );
    }
  ]
);

chatServices.factory('User',
  [ '$resource',
    function ($resource)
    {
      return $resource('rest/User/:action',
        {},
        {   login   : {method : 'POST', params  : {action : 'Login'}, isArray : false}
          , addUser : {method : 'POST', params  : {action : 'Add'},   isArray : false}
          , delUser : {method : 'POST', params : {action : 'Remove'}, isArray : false}
        }
      );
    }
  ]
);
