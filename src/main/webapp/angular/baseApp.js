var app = angular.module('baseApp',
        ['pascalprecht.translate', 'ngResource', 'angular-growl']);

app.config(
        function ($translateProvider) {
            $translateProvider.useStaticFilesLoader({
                prefix: 'i18n/locale_',
                suffix: '.json'
            });
            var userLang = navigator.language || navigator.userLanguage;
            userLang = userLang.substr(0, 2);
            $translateProvider.preferredLanguage(userLang);
            $translateProvider.fallbackLanguage("en");
        }
);

app.constant('ENDPOINT_URI', 'http://localhost:8080/app/');

app.factory('Entity', function ($resource) {
    return $resource('/app/rest/:classe/:id', {classe: '@classe', id: '@id'});
});


app.directive('sidemenu', ['$location', '$http', function () {
        return {
            templateUrl: 'templates/menu/menu.html',
            restrict: 'E',
            replace: true,
            scope: {
            },
            controller:
                    function ($scope, $http) {
                        $scope.menus = {};
                        $http.get('app/user/menu')
                                .success(function (data) {
                                    $scope.menus = data;
                                });
                        $scope.selectedMenu = 'blank';
                        $scope.collapseVar = 0;
                        $scope.multiCollapseVar = 0;

                        $scope.check = function (x) {
                            if (x == $scope.collapseVar)
                                $scope.collapseVar = 0;
                            else
                                $scope.collapseVar = x;
                        };

                        $scope.multiCheck = function (y) {
                            if (y == $scope.multiCollapseVar)
                                $scope.multiCollapseVar = 0;
                            else
                                $scope.multiCollapseVar = y;
                        };
                    }
        }
    }]);

app.controller('mainControllerBase', function ($scope, growl, Entity) {
    $scope.saveEntity = function (entity) {
        $scope.entity = Entity.save(entity);
        $scope.entity.$promise.then(function (data) {
            growl.success("<b>Salvo</b> com sucesso");
            $scope.successSaveEntity(data);
            $scope.entities = Entity.query($scope.entitySearch);
        }, function (error) {
            growl.error("<b>Falha</b> ao salvar: " + error);
        });
    };

    $scope.errorSaveEntity = function (error) {

    };

    $scope.successSaveEntity = function (data) {

    };

    $scope.query = function (args) {
        $scope.entities = Entity.query(args);
    };
});


app.controller('mainController', function ($scope, $controller, Entity) {
    angular.extend(this, $controller('mainControllerBase', {$scope: $scope}));
});


