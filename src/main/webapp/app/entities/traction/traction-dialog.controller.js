(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('TractionDialogController', TractionDialogController);

    TractionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Traction', 'Car'];

    function TractionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Traction, Car) {
        var vm = this;

        vm.traction = entity;
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
            if (vm.traction.id !== null) {
                Traction.update(vm.traction, onSaveSuccess, onSaveError);
            } else {
                Traction.save(vm.traction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('springCarsApp:tractionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
