app.controller("BooksCtrl", ["$scope", "Books", function ($scope, booksService) {

  booksService.list().then(function(books) {
    //console.log(books[0]);
    // FIXME : do not know why I have to go in an array to have my result
    $scope.books = books[0];
  })

}]);
