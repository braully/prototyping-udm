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
    $scope.model = {
        classe: '',
        entities: {},
        entity: {},
        search: {},
        currentPage: 1,
        numPerPage: 5,
        totalItems: 0,
        totalPages: 0
    };

    $scope.saveEntity = function (entity) {
        $scope.model.entity = Entity.save(entity);
        $scope.model.entity.$promise.then(function (data) {
            growl.success("<b>Save</b> successful");
            $scope.successSaveEntity(data);
            $scope.model.entities = Entity.query($scope.model.search);
        }, function (error) {
            growl.error("<b>Fail</b> on save: " + error);
        });
    };

    $scope.query = function (args) {
        $scope.model.entities = Entity.query(args);
    };

    $scope.query = function () {
        $scope.model.search.classe = $scope.model.classe;
        $scope.model.entities = Entity.query($scope.model.search);
    };


    $scope.paginate = function (value) {
        var begin, end, index;
        begin = ($scope.model.currentPage - 1) * $scope.model.numPerPage;
        end = begin + $scope.numPerPage;
        index = $scope.model.entities.indexOf(value);
        return (begin <= index && index < end);
    };

    $scope.setPage = function () {
        $scope.model.currentPage = this.n;
    };

    $scope.prevPage = function () {
        if ($scope.model.currentPage > 1) {
            $scope.model.currentPage--;
        }
    };

    $scope.nextPage = function () {
        if ($scope.model.currentPage * $scope.model.numPerPage < $scope.model.entities.length) {
            $scope.model.currentPage++;
        }
    };

    $scope.errorSaveEntity = function (error) {

    };

    $scope.successSaveEntity = function (data) {

    };
});


app.controller('mainController', function ($scope, $controller, Entity) {
    angular.extend(this, $controller('mainControllerBase', {$scope: $scope}));
});


