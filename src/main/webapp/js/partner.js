angular.module('baseApp').controller('partnerController', function ($scope, $controller) {
    angular.extend(this, $controller('mainControllerBase', {$scope: $scope}));
    
    $scope.partners = [];
    $scope.partner = {classe: 'partner'};
});

