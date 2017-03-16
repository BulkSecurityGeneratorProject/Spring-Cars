(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('GasDialogController', GasDialogController);

    GasDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gas', 'Car'];

    function GasDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Gas, Car) {
        var vm = this;

        vm.gas = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cars = Car.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gas.id !== null) {
                Gas.update(vm.gas, onSaveSuccess, onSaveError);
            } else {
                Gas.save(vm.gas, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('springCarsApp:gasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
