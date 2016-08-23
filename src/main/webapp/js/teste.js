angular.module('baseApp').controller('mainController', function ($scope, $controller, Entity) {
    angular.extend(this, $controller('mainControllerBase', {$scope: $scope}));
//    $scope.partner = Entity.get({classe: 'partner', id: '@id'})
    $scope.objects = Entity.query({classe: 'partner'});
    $scope.partners = $scope.objects;
    $scope.partner = [];
    $scope.partner.classe = 'partner';

    $scope.objects.$promise.then(function () {
        $scope.totalItems = $scope.objects.length;
    });
    $scope.currentPage = 1;
    $scope.numPerPage = 5;

    $scope.totalPages = Math.ceil($scope.objects.length / $scope.numPerPage);

    $scope.paginate = function (value) {
        var begin, end, index;
        begin = ($scope.currentPage - 1) * $scope.numPerPage;
        end = begin + $scope.numPerPage;
        index = $scope.objects.indexOf(value);
        return (begin <= index && index < end);
    };

    $scope.setPage = function () {
        $scope.currentPage = this.n;
    };

    $scope.prevPage = function () {
        if ($scope.currentPage > 1) {
            $scope.currentPage--;
        }
    };

    $scope.nextPage = function () {
        if ($scope.currentPage * $scope.numPerPage < $scope.objects.length) {
            $scope.currentPage++;
        }
    };
});
