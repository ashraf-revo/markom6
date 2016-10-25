angular.module("Markom").controller("HomeCtrl", function ($scope, Auth, $state) {
    $scope.logout = function () {
        Auth.logout().then(function () {
            $state.go("login")
        })
    };
});