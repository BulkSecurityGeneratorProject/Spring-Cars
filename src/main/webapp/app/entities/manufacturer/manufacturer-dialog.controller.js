(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('ManufacturerDialogController', ManufacturerDialogController);

    ManufacturerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Manufacturer', 'Car'];

    function ManufacturerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Manufacturer, Car) {
        var vm = this;

        vm.manufacturer = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.manufacturer.id !== null) {
                Manufacturer.update(vm.manufacturer, onSaveSuccess, onSaveError);
            } else {
                Manufacturer.save(vm.manufacturer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('springCarsApp:manufacturerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.yearFund = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
