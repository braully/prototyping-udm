/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module('baseApp').controller('mainController', function ($scope, EntityFactory) {
    $scope.partner = [];
    $scope.partners = EntityFactory.query({entity: 'partner'});
});

