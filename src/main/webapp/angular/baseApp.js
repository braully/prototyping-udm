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

app.directive('modal', ['$location', '$http', 'growl', 'Entity', function () {
        return {
            templateUrl: 'templates/modal/modal.html',
            restrict: 'E',
            replace: true,
            scope: {
                bodyUrl: '@',
                title: '@',
                entity: '=entity',
                classe: '@',
                htmlId: '@'
            },
            controller:
                    function ($scope, $element, growl, Entity) {
                        $scope.model = {entity: $scope.entity, classe: $scope.classe};
                        $scope.saveEntity = function () {
                            $scope.entity.classe = $scope.classe;
                            $scope.entity = Entity.save($scope.entity);
                            $scope.entity.$promise.then(function (data) {
                                growl.success("<b>Save</b> successful");
                                $element.modal('hide');
                            }, function (error) {
                                growl.error("<b>Fail</b> on save: " + error);
                            });
                        };
                        $element.modal('show');
                    }
        };
    }
]);


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

app.controller('controllerBase', function ($compile, $scope, growl, Entity) {
    $scope.model = {
        classe: '',
        entities: {},
        entity: {},
        search: {},
        selectList: {},
        currentPage: 0,
        numPerPage: 5,
        totalItems: 0,
        totalPages: 5
    };

    $scope.numToArray = function (num) {
//        console.log('var to array: ' + num);
        var array = new Array(num);
        for (var i = 0; i < num; i++) {
            array[i] = i;
        }
//        console.log(array);
        return array;
    };

    $scope.modalOpen = function (classe, name, url) {
        var namesanitized = name.replace(/\./g, '_');
        var modalChild = document.getElementById(name);
        if (!modalChild) {
            var modal = angular.element(document.getElementById('modal'));
            modal.append($compile("<modal html-id='" + namesanitized + "' body-url='" + url + "' "
                    + "title='New " + classe + "' classe='"
                    + classe + "' entity='" + name + "'></modal>")($scope));
        } else {
            $('#' + namesanitized).modal('show');
        }
    };

    $scope.selectList = function (args) {
        if (args && !$scope.model.selectList[args]) {
            $scope.model.selectList[args] = Entity.query({classe: args});
        }
    };

    $scope.saveEntity = function () {
        $scope.model.entity.classe = $scope.model.classe;
        $scope.model.entity = Entity.save($scope.model.entity);
        $scope.model.entity.$promise.then(function (data) {
            growl.success("<b>Save</b> successful");
            $scope.successSaveEntity(data);
            $scope.query();
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
        begin = $scope.model.currentPage * $scope.model.numPerPage;
        end = begin + $scope.model.numPerPage;
        index = $scope.model.entities.indexOf(value);
//        console.log("i: " + index + " b: " + begin + " e: " + end);
        return (begin <= index && index < end);
    };

    $scope.setPage = function (n) {
        $scope.model.currentPage = n;
    };

    $scope.prevPage = function () {
        if ($scope.model.currentPage > 0) {
            $scope.model.currentPage--;
        }
    };

    $scope.nextPage = function () {
        if (($scope.model.currentPage + 1) * $scope.model.numPerPage < $scope.model.entities.length) {
            $scope.model.currentPage++;
        }
    };

    $scope.errorSaveEntity = function (error) {

    };

    $scope.successSaveEntity = function (data) {

    };
});


app.controller('mainController', function ($scope, $controller, Entity) {
    angular.extend(this, $controller('controllerBase', {$scope: $scope}));
});