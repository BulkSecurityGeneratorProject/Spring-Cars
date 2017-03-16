(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('ClassificationDetailController', ClassificationDetailController);

    ClassificationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Classification', 'Car'];

    function ClassificationDetailController($scope, $rootScope, $stateParams, previousState, entity, Classification, Car) {
        var vm = this;

        vm.classification = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('springCarsApp:classificationUpdate', function(event, result) {
            vm.classification = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
