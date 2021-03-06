/*global define */

'use strict';

define(['angular'], function(angular) {

/* Directives */

angular.module('DashboardApp.directives', []).
  directive('appVersion', ['version', function(version) {
    return function(scope, elm, attrs) {
      elm.text(version);
    };
  }]);

});
