angular.module('baseApp').controller('mainController', function ($scope, $controller, Entity) {
    angular.extend(this, $controller('mainControllerBase', {$scope: $scope}));
//    $scope.partner = Entity.get({classe: 'partner', id: '@id'})
//    $scope.partners = Entity.query({classe: 'partner'});
    $scope.partner = [];
    $scope.partner.classe = 'partner';
});

