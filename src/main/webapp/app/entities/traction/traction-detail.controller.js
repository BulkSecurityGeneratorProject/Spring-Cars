(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('TractionDetailController', TractionDetailController);

    TractionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Traction', 'Car'];

    function TractionDetailController($scope, $rootScope, $stateParams, previousState, entity, Traction, Car) {
        var vm = this;

        vm.traction = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('springCarsApp:tractionUpdate', function(event, result) {
            vm.traction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
