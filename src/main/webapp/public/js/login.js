angular.module("myApp").controller("loginCtrl", ['$scope', '$http', '$state', function ($scope, $http, $state) {

    $scope.userId;
    $scope.password;
    $scope.login = function () {
        $http.get('logout').then(function (response) {
            $scope.invalidCredentials = false;

            var headers = {
                "Authorization": "Basic " + btoa($scope.userId + ":" + $scope.password)
            };

            $http.get('getLoggedInUser', {
                headers: headers
            }).then(function (response) {
                $scope.invalidCredentials = false;
                $scope.details = response.data;
                debugger;
                if ($scope.details.registrationDone == false) {
                    $state.go('instructions', {
                        'id': $scope.details.id
                    });
                } else {
                    $state.go("exam", {
                        'id': $scope.details.id
                    });
                }
            }, function (error) {
                $scope.invalidCredentials = true;
            });
        }, function (error) {});
    }


}]);