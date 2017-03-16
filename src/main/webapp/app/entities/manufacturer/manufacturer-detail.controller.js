(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('ManufacturerDetailController', ManufacturerDetailController);

    ManufacturerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Manufacturer', 'Car'];

    function ManufacturerDetailController($scope, $rootScope, $stateParams, previousState, entity, Manufacturer, Car) {
        var vm = this;

        vm.manufacturer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('springCarsApp:manufacturerUpdate', function(event, result) {
            vm.manufacturer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
