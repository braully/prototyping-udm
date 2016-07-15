'use strict';
/**
 * @ngdoc overview
 * @name baseSimpleFinanceApp
 * @description
 * # baseSimpleFinanceApp
 *
 * Main module of the application.
 */
angular.module('baseSimpleFinanceApp', [
    'oc.lazyLoad',
    'ui.router',
    'ui.bootstrap',
    'angular-loading-bar'
]).config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider',
    function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {
        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });

        $urlRouterProvider.otherwise('/dashboard/blank');

        $stateProvider
                .state('dashboard', {
                    url: '/dashboard',
                    templateUrl: 'views/dashboard/main.html',
                    resolve: {
                        loadMyDirectives: function ($ocLazyLoad) {
                            return $ocLazyLoad.load(
                                    {
                                        name: 'baseSimpleFinanceApp',
                                        files: [
                                            'templates/header/header.js',
                                            'templates/header/header-notification/header-notification.js',
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
                .state('dashboard.blank', {
                    templateUrl: 'views/pages/blank.html',
                    url: '/blank'
                })
                .state('login', {
                    templateUrl: 'views/pages/login.html',
                    url: '/login'
                })
    }]);


