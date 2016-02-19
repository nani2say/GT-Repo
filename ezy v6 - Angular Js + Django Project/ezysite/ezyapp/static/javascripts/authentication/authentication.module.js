
// i dont thik this file is used

(function () {
  'use strict';

  angular
    .module('ezy.authentication', [
      'ezy.authentication.controllers',
      'ezy.authentication.services'
    ]);

  angular
    .module('ezy.authentication.controllers', []);

  angular
    .module('ezy.authentication.services', ['ngCookies']);
})();