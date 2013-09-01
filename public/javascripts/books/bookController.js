app.controller("BookController", ["$scope", "Books", function ($scope, booksService) {
 
  $scope.update = function(book) {
    booksService.add(book).then(function(bookId) {
      $scope.bookId = bookId;
    });
  };
}]);

