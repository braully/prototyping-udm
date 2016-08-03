var app = angular.module('baseSimpleFinanceApp',
        ['ngRoute', 'pascalprecht.translate']);

app.config(
        function ($routeProvider, $translateProvider) {

            $translateProvider.useStaticFilesLoader({
                prefix: 'i18n/locale_',
                suffix: '.json'
            });

            var userLang = navigator.language || navigator.userLanguage;
            userLang = userLang.substr(0, 2);
            $translateProvider.preferredLanguage(userLang);
            $translateProvider.fallbackLanguage("en");

            $routeProvider.when('/', {
                templateUrl: 'views/blank.html',
                controller: 'mainController'
            }).when('/blank', {
                templateUrl: 'views/blank.html',
                controller: 'mainController'
            }).when('/partner', {
                templateUrl: 'app/component/form/partner',
                controller: 'mainController'
            }).when('/app/:name*', {
                templateUrl: function (urlattr) {
                    return 'app/component/form/' + urlattr.name + '';
                },
                controller: 'mainController'
            });
        }
);

app.controller('mainController', function ($scope) {

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
