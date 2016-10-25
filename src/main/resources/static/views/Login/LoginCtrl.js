angular.module("Markom").controller("LoginCtrl", function ($scope, Auth, $state) {
    $scope.user = {username: '', password: ''};
    $scope.login = function () {
        Auth.login($scope.user).then(function () {
            $state.go("home");
        }).catch(function () {
            $scope.user = {username: '', password: ''};
        })
    };
});