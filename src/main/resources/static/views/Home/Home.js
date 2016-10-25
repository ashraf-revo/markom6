angular.module("Markom")
    .config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/");
        $stateProvider.state("home", {
            parent: "site",
            url: "/",
            requireAuth: true,
            roles: ["ROLE_USER"],
            views: {
                "content@": {
                    templateUrl: "views/Home/home.html",
                    controller: 'HomeCtrl'
                }
            }
        });
    });