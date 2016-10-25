angular.module("Markom")
    .factory("Auth", function (URL, $http, localStorageService, $state) {
        return {
            authorize: function (toState) {
                if (toState.requireAuth == true) {
                    if (this.IsLoggedIn()) {
                        if (!this.CanAccess(toState.roles)) {
                            $state.go("error")
                        }
                    }
                    else {
                        $state.go("login")
                    }
                } else if (toState.name == "login" && this.IsLoggedIn())$state.go("home");
            },
            login: function (user) {
                return $http.post(URL + 'oauth/token',
                    "username=" + user.username + "&password=" + user.password + "&grant_type=password&client_secret=revo&client_id=revo",
                    {
                        headers: {"Content-Type": "application/x-www-form-urlencoded"}
                    }).success(function (response) {
                    localStorageService.set('token', response);
                    return response;
                });
            },
            logout: function () {
                return $http.post(URL + 'logout', {}).success(function () {
                    localStorageService.clearAll();
                });
            },
            Current: function () {
                return localStorageService.get("token").user;
            },
            /**
             * @return {boolean}
             */
            IsLoggedIn: function () {
                return localStorageService.get("token") != null;
            },
            /**
             * @return {boolean}
             */
            CanAccess: function (r) {
                if (this.IsLoggedIn()) {
                    var roles = this.Current().roles;
                    for (var i = 0; i < roles.length; i++) {
                        if (r.indexOf(roles[i]) == -1) {
                            return false;
                        }
                    }
                    return true;
                }
                else return false;
            }
        };
    }).factory('authInterceptor', function ($rootScope, $q, $location, localStorageService) {
    return {
        request: function (config) {
            config.headers = config.headers || {};
            var token = localStorageService.get('token');
            if (token != null) {
                config.headers.Authorization = 'Bearer ' + token.access_token;
            }
            return config;
        }
    };
})
    .factory('authExpiredInterceptor', function ($rootScope, $q, $injector, localStorageService) {
        return {
            responseError: function (response) {
                if (response.status == 406 || (response.status === 401 && (response.data.error == 'invalid_token' || response.data.error == 'Unauthorized'))) {
                    localStorageService.remove('token');
                    var $state = $injector.get('$state');
                    var $timeout = $injector.get('$timeout');
                    $timeout(function () {
                        $state.go("login")
                    });
                }
                return $q.reject(response);
            }
        };
    });