/*global require, requirejs */

'use strict';

requirejs.config({
  paths: {
    'angular'       : ['../lib/angularjs/angular'],
    'angular-route' : ['../lib/angularjs/angular-route'],
    'smart-table'   : ['../lib/smart-table/smart-table'],
    'd3js'          : ['../lib/d3js/d3'],
    'nv.d3'         : ['../lib/nvd3/nv.d3'],
    'angular-nvd3'  : ['../lib/angular-nvd3/angular-nvd3']

  },
  shim: {
    'angular'                : {                                     exports : 'angular' },
    'angular-route'          : { deps : ['angular'],                 exports : "" },
    'smart-table'            : {                                     exports : 'smart-table' },
    'd3js'                   : {                                     exports : "d3"},
    'nv.d3'                  : { deps : ['d3js'],                    exports : "" },
    'angular-nvd3'           : { deps : ['nv.d3', 'd3js'],           exports : 'nvd3'},
  
  }
});

require(['angular', './dashboard/controllers', './dashboard/directives', './dashboard/filters', './dashboard/services', 'angular-route', 'smart-table', 'angular-nvd3'],
  function(angular, controllers) {

    // Declare app level module which depends on filters, and services

    angular.module('DashboardApp', ['DashboardApp.filters', 'DashboardApp.services', 'DashboardApp.directives', 'ngRoute', 'ng', 'smart-table','nvd3']).
      config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/',               {templateUrl: 'assets/javascripts/partials/home.html',           controller: controllers.HomeCtrl});
        $routeProvider.when('/infrastructure', {templateUrl: 'assets/javascripts/partials/infrastructure.html', controller: controllers.InfrastructureCtrl});
        $routeProvider.when('/integrations',   {templateUrl: 'assets/javascripts/partials/integrations.html',   controller: controllers.IntegrationsCtrl});
        $routeProvider.otherwise({redirectTo: '/'});
      }]);

    angular.bootstrap(document, ['DashboardApp']);

});
