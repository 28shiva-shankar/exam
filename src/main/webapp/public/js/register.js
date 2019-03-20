angular.module("myApp").controller("registerCtrl", ['$scope', '$http', '$state', function ($scope, $http, $state) {
    $scope.isFormSubmitted = false;
    $scope.student = {};

    $scope.isValid = function () {
        $scope.isFormSubmitted = true;
        return $scope.userForm.$valid;
    }

    $scope.submit = function (student) {
        if ($scope.isValid()) {
            $http.post('register', student).then(function (response) {
                toastr.success("student registration successful");
                $state.go('login');
            }, function (error) {
                toastr.error(error.data.message);
            });
        }
    }

    $scope.filterValue = function ($event) {
        if (isNaN(String.fromCharCode($event.keyCode))) {
            $event.preventDefault();
        }
    };



    $scope.clear = function () {
        $scope.userForm.$setPristine();
        $scope.student.studentName = "";
        $scope.student.emailId = "";
        $scope.student.mobileNo = "";
        $scope.student.rollNo = "";
    }
}]);