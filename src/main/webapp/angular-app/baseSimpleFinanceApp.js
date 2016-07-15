'use strict';
/**
 * @ngdoc overview
 * @name baseSimpleFinanceApp
 * @description
 * # baseSimpleFinanceApp
 *
 * Main module of the application.
 */
var app = angular.module('baseSimpleFinanceApp', [
    'oc.lazyLoad',
    'ui.router',
    'ui.bootstrap',
    'angular-loading-bar',
    'pascalprecht.translate']).config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider', '$translateProvider',
    function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $translateProvider) {
        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });

        $translateProvider.useStaticFilesLoader({
            prefix: 'i18n/locale_',
            suffix: '.json'
        });

        $translateProvider.preferredLanguage('en');
        $translateProvider.fallbackLanguage("en");

        $urlRouterProvider.otherwise('/views/blank');

        $stateProvider.state('views', {
            url: '/views',
            templateUrl: 'views/main.html',
            resolve: {
                loadMyDirectives: function ($ocLazyLoad) {
                    return $ocLazyLoad.load(
                            {
                                name: 'baseSimpleFinanceApp',
                                files: [
                                    'templates/header/header.js',
                                    'templates/menu/menu.js'
                                ]
                            }),
                            $ocLazyLoad.load(
                                    {
                                        name: 'toggle-switch',
                                        files: ["bower_components/angular-toggle-switch/angular-toggle-switch.min.js",
                                            "bower_components/angular-toggle-switch/angular-toggle-switch.css"
                                        ]
                                    }),
                            $ocLazyLoad.load(
                                    {
                                        name: 'ngAnimate',
                                        files: ['bower_components/angular-animate/angular-animate.js']
                                    })
                    $ocLazyLoad.load(
                            {
                                name: 'ngCookies',
                                files: ['bower_components/angular-cookies/angular-cookies.js']
                            })
                    $ocLazyLoad.load(
                            {
                                name: 'ngResource',
                                files: ['bower_components/angular-resource/angular-resource.js']
                            })
                    $ocLazyLoad.load(
                            {
                                name: 'ngSanitize',
                                files: ['bower_components/angular-sanitize/angular-sanitize.js']
                            })
                    $ocLazyLoad.load(
                            {
                                name: 'ngTouch',
                                files: ['bower_components/angular-touch/angular-touch.js']
                            })
                }
            }
        })
                .state('views.blank', {
                    templateUrl: 'views/blank.html',
                    url: '/blank'
                })
                .state('login', {
                    templateUrl: 'views/login.html',
                    url: '/login'
                })
    }]);

app.config(function ($translateProvider) {
    $translateProvider.translations('en', {
        TITLE: 'Hello',
        FOO: 'This is a paragraph.',
        BUTTON_LANG_EN: 'english',
        BUTTON_LANG_DE: 'german'
    });
    $translateProvider.translations('de', {
        TITLE: 'Hallo',
        FOO: 'Dies ist ein Paragraph.',
        BUTTON_LANG_EN: 'englisch',
        BUTTON_LANG_DE: 'deutsch'
    });
    $translateProvider.preferredLanguage('en');
});

app.controller('Ctrl', function ($scope, $translate) {
    $scope.changeLanguage = function (key) {
        $translate.use(key);
    };
});
