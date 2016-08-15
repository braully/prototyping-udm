var app = angular.module('baseApp',
        ['pascalprecht.translate', 'ngResource']);

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

app.controller('mainController', function ($scope) {

});
