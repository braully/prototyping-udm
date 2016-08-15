angular.module('baseApp').controller('mainController', function ($scope, EntityFactory) {
    $scope.partner = [];
//    $scope.partner = EntityFactory.get({entity: 'partner', id: '@id'})
    $scope.partners = EntityFactory.query({entity: 'partner'});
});

