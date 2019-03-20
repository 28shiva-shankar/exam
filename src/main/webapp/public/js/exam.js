angular.module("myApp").controller(
    "examCtrl", [
        "$scope",
        "$http",
        "$timeout", "$stateParams",
        function ($scope, $http, $timeout, $stateParams) {
            $scope.id = $stateParams.id;
            $scope.isAptiActive = false;
            $scope.isLogicalActive = false;
            $scope.isTechnicalActive = false;
            $http.get("registration-info?id=" + $scope.id).then(
                function (response) {
                    $scope.student = response.data;
                    if (localStorage.getItem('category') == null) {
                        $scope.loadQuestions("APTITUDE");
                        $scope.isAptiActive = true;

                    } else {
                        $scope.loadQuestions(localStorage.getItem('category'));
                        if (localStorage.getItem('category') == 'APTITUDE') {
                            $scope.isAptiActive = true;
                        } else if (localStorage.getItem('category') == 'LOGICAL') {
                            $scope.isLogicalActive = true;
                        } else {
                            $scope.isTechnicalActive = true;
                        }
                    }
                    $scope.refreshTime();
                },
                function (error) {
                    toastr.error("Failed to fetch details.");
                });

            $scope.initaliseWatchers = function (category) {
                angular.forEach($scope.answers[category], function (answer, index) {
                    $scope.$watch("answers." + category + "[" + index + "]", function (answer, oldAnswer) {
                        if (answer == null || oldAnswer == null) {
                            return;
                        }
                        if (answer.answer == oldAnswer.answer) {
                            return;
                        }
                        $http.post("save", {
                                answer: answer.answer,
                                id: answer.id
                            })
                            .then(function (response) {});
                    }, true);
                });
            }

            $scope.answers = {};
            $scope.answer = {};
            $scope.index = {};
            $scope.loadQuestions = function (category) {
                if (!$scope.answers[category]) {
                    $http.get("getAnswersByCategory?id=" + $scope.id + "&category=" + category).then(function (response) {
                        $scope.answers[category] = response.data;
                        if (localStorage.getItem(category) == null) {
                            $scope.index[category] = 0;
                            localStorage.setItem(category, 0);
                        } else {
                            $scope.index[category] = parseInt(localStorage.getItem(category));
                        }
                        $scope.answer = $scope.answers[category][$scope.index[category]];
                        $scope.initaliseWatchers(category);
                        localStorage.setItem('category', category);
                    }, function (error) {
                        toastr.error("Failed to load questions.");
                    });
                } else {
                    $scope.answer = $scope.answers[category][$scope.index[category]];
                    localStorage.setItem(category, $scope.index[category]);
                }
            };

            $scope.loadQuestion = function (category, id) {
                $scope.index[category] = id;
                $scope.loadQuestions(category)
            }

            $timeout(function () {
                $scope.isInitDone = true;
            })
            $scope.refreshTime = function () {
                var date = new Date();
                $scope.timeLeft = new Date(
                        $scope.student.endTime +
                        date.getTimezoneOffset() *
                        60000) -
                    date;
                if (($scope.timeLeft - (date
                        .getTimezoneOffset() * 60000)) <= 0) {
                    $scope.submit();
                } else {
                    $timeout($scope.refreshTime, 1000);
                }
            };
            $scope.getCount = function (count) {
                return new Array(count);
            };

            $scope.isOptionSelected = function (value,
                option, isMulti) {
                option = option + "";
                if (isMulti) {
                    return value != null &&
                        value.indexOf(option) > -1;
                } else {
                    return value == option;
                }
            };
            $scope.selectOption = function (answer, value,
                option, isMulti) {
                option = option + "";
                if (isMulti) {
                    var answers = value == null ? [] :
                        value.split("");
                    var index = answers.indexOf(option);
                    if (index > -1) {
                        answers.splice(index, 1);
                    } else {
                        answers.push(option);
                        answers.sort();
                    }
                    answer.answer = answers.join("");
                } else {
                    if (value == option) {
                        answer.answer = null;
                    } else {
                        answer.answer = option;
                    }
                }
            };

            $scope.submitClick = function () {
                if (confirm(
                        'Are you sure you want to Submit? Please make sure you have answered all the questions before clicking OK.'
                    )) {
                    $scope.submit();
                    $scope.get('logout').then(function (response) {
                        
                    }, function (error) {

                    });
                }
            }
            $scope.submit = function () {
                $http.post("submit?id=" + $scope.student.id, {}).then(function (response) {
                    $scope.student = response.data;
                    $scope.answers = null;
                }, function (error) {
                    toastr.error("Could not submit.")
                });
            }

            $scope.previous = function (category) {
                $scope.index[category]--;
                localStorage.setItem(category, $scope.index[category]);
                $scope.loadQuestions(category);
            }
            $scope.next = function (category) {
                $scope.index[category]++;
                localStorage.setItem(category, $scope.index[category]);
                $scope.loadQuestions(category);
            }
        }
    ]);