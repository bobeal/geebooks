app.factory("Books", ["Restangular", function (Restangular) {
  function list () {
    return Restangular.all("books").getList();
  }

  function add(book) {
    var promise = Restangular.all("book").post(book).then(
      function(bookId) {
        return bookId;
      });
    return promise;
  }

  return {
    list: list,
    add: add
  }
}]);
