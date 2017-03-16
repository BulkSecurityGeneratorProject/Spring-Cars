(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('GasDetailController', GasDetailController);

    GasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Gas', 'Car'];

    function GasDetailController($scope, $rootScope, $stateParams, previousState, entity, Gas, Car) {
        var vm = this;

        vm.gas = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('springCarsApp:gasUpdate', function(event, result) {
            vm.gas = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
