angular.module("Markom")
    .config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/");
        $stateProvider.state("login", {
            parent: "site",
            url: '/login',
            requireAuth: false,
            roles: [],
            views: {
                "content@": {
                    templateUrl: "views/Login/login.html",
                    controller: 'LoginCtrl'
                }
            }
        })
    });