app.controller("BooksCtrl", ["$scope", "$location", "$stateParams", "Books", function ($scope, $location, $stateParams, booksService) {

  $scope.books = [
    { id : "123", isbn : "isbn-123", title : "Livre 1", author : "BOR"},
    { id : "456", isbn : "isbn-456", title : "Livre 2", author : "VSI"}
  ]
}]);
