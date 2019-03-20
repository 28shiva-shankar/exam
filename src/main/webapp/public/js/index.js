var app = angular.module("myApp", ["ui.router"]);

app.config(function ($stateProvider, $urlRouterProvider, $httpProvider, $qProvider) {

    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('login', {
            url: '/',
            templateUrl: 'public/login.html'
        })
        .state('register', {
            url: '/register',
            templateUrl: 'public/register.html'
        })
        .state('instructions', {
            url: '/instructions/:id',
            templateUrl: 'public/instructions.html'
        })
        .state('exam', {
            url: '/exam/:id',
            templateUrl: 'public/exam.html'
        });
    $qProvider.errorOnUnhandledRejections(false);
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
});