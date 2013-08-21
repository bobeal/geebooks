app.factory("Books", ["Restangular", function (Restangular) {
  function list () {
    return Restangular.all("books").getList();
  }

  return {
    list: list
  }
}]);
