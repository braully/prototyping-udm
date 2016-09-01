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


app.directive('ng-autocomplete', function ($http, $interpolate, $parse) {
    return {
        restrict: 'A',
        require: 'ngModel',
        compile: function (scope, elem, attr) {
            elem.autocomplete({
                source: function (request, response) {
                    $http({url: '/app/rest/partner', params: {name: request.term}})
                            .success(function (data) {
                                mappedItems = $.map(data, function (item) {
                                    var result = {};

                                    if (typeof item === "string") {
                                        result.label = item;
                                        result.value = item;

                                        return result;
                                    }

                                    result.label = $interpolate(labelExpression)(item);

                                    if (attrs.value) {
                                        result.value = item[attrs.value];
                                    } else {
                                        result.value = item;
                                    }

                                    return result;
                                });

                                return response(mappedItems);
                            });
                },
                minLength: 1,
                select: function (event, selectedItem) {
                    // Do something with the selected item, e.g. 
//                    scope.yourObject = selectedItem.item.value;
                    scope.$apply();
                    event.preventDefault();
                }
            });
        }
    };
});

app.directive('jautocomplete', function ($http, $interpolate, $parse) {
    return {
        restrict: 'E',
        replace: true,
        template: '<input type="text" />',
        require: 'ngModel',
        compile: function (elem, attrs) {
            var modelAccessor = $parse(attrs.ngModel),
                    labelExpression = attrs.label;

            return function (scope, element, attrs, controller) {
                var mappedItems = null;
                var allowCustomEntry = attrs.allowCustomEntry || false;

                element.autocomplete({
                    source: function (request, response) {
                        $http({
                            url: attrs.url,
                            method: 'GET',
                            params: {name: request.term}
                        })
                                .success(function (data) {
                                    mappedItems = $.map(data, function (item) {
                                        var result = {};

                                        if (typeof item === "string") {
                                            result.label = item;
                                            result.value = item;

                                            return result;
                                        }

                                        result.label = $interpolate(labelExpression)(item);

                                        if (attrs.value) {
                                            result.value = item[attrs.value];
                                        } else {
                                            result.value = item;
                                        }

                                        return result;
                                    });

                                    return response(mappedItems);
                                });
                    }, select: function (event, ui) {
                        scope.$apply(function (scope) {
                            modelAccessor.assign(scope, ui.item.value);
                        });

                        elem.val(ui.item.label);
                        event.preventDefault();
                    }, change: function (event, ui) {
                        var currentValue = elem.val(), matchingItem = null;

                        if (allowCustomEntry) {
                            return;
                        }

                        for (var i = 0; i < mappedItems.length; i++) {
                            if (mappedItems[i].label === currentValue) {
                                matchingItem = mappedItems[i].label;
                                break;
                            }
                        }

                        if (!matchingItem) {
                            scope.$apply(function (scope) {
                                modelAccessor.assign(scope, null);
                            });
                        }
                    }
                });
            }
        }
    }
});

app.controller('controllerBase', function ($scope, growl, Entity) {
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
    angular.extend(this, $controller('controllerBase', {$scope: $scope}));
});