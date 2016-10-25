angular.module("Markom", ["ui.router", "LocalStorageModule"])
    .constant("URL", 'http://localhost:8080/')
    .run(function ($rootScope, Auth, $state, $timeout) {
        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
            $timeout(function () {
                Auth.authorize(toState);
            })
        });
    })
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
        $urlRouterProvider.otherwise("/");
        $stateProvider.state("site", {
            abstract: true,
            requireAuth: false,
            roles: []
        });
        $httpProvider.interceptors.push('authInterceptor');
        $httpProvider.interceptors.push('authExpiredInterceptor');
    });