angular.module("myApp").controller("instructionsCtrl", ['$scope', '$http', '$state', '$stateParams', function ($scope, $http, $state, $stateParams) {
    $scope.stdId = $stateParams.id;
    $scope.begin = function () {
        localStorage.clear();
        $http.post("start?id=" + $scope.stdId).then(
            function (response) {
                $state.go('exam', {
                    'id': $scope.stdId
                });
            },
            function (error) {
                toastr.error("An error occurred while processing your request.");
            });
    }
}])