'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('baseSimpleFinanceApp')
        .directive('header', function () {
            return {
                templateUrl: 'templates/header/header.html',
                restrict: 'E',
                replace: true,
            }
        });


