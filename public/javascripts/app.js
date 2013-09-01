var app = angular.module("app",
  ["ngResource", "restangular", "http-auth-interceptor", "ui.state", "ui.bootstrap", "ui.utils"])
  .constant("Config", {
    apiUrl: "http://localhost:9000\\:9000/api"
  })
  .constant("Events", {

  })
  .config(["RestangularProvider", function (RestangularProvider) {
    "use strict";
    RestangularProvider.setBaseUrl('/api');
  }])
  .config(["$locationProvider", "$stateProvider", "$urlRouterProvider",
    function ($locationProvider, $stateProvider, $urlRouterProvider) {
    "use strict";
    $locationProvider.html5Mode(true).hashPrefix("!");

    $stateProvider
      .state("index", {
        url: "/",
        templateUrl: "/views/index"
      })
      .state("books", {
        url: "/books",
        controller: "BooksCtrl",
        templateUrl: "/views/books/list"
      })
      .state("book", {
        url: "/book/add",
        controller: "BookController",
        templateUrl: "/views/books/add"
      });

      $urlRouterProvider.otherwise("/");
  }])
  .run(["$rootScope", "$timeout", "$state", "$stateParams", "$log", "$location",
    function ($rootScope, $timeout, $state, $stateParams, $log, $location) {
      // Global config
      $rootScope.$log = $log;
      $rootScope.$location = $location;
      $rootScope.$state = $state;
      $rootScope.$stateParams = $stateParams;
  }]);
