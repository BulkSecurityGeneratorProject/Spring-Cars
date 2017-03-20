(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('CarDialogController', CarDialogController);

    CarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Car', 'Manufacturer', 'Link', 'Classification', 'Traction', 'Gas', 'Photo'];

    function CarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Car, Manufacturer, Link, Classification, Traction, Gas, Photo) {
        var vm = this;

        vm.car = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.manufacturers = Manufacturer.query();
        vm.links = Link.query();
        vm.classifications = Classification.query();
        vm.tractions = Traction.query();
        vm.gases = Gas.query();
        vm.photos = Photo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.car.id !== null) {
                Car.update(vm.car, onSaveSuccess, onSaveError);
            } else {
                Car.save(vm.car, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('springCarsApp:carUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.year = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
