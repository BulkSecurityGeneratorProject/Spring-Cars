(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('ClassificationDialogController', ClassificationDialogController);

    ClassificationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Classification', 'Car'];

    function ClassificationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Classification, Car) {
        var vm = this;

        vm.classification = entity;
        vm.clear = clear;
        vm.save = save;
        vm.classifications = Classification.query();
        vm.cars = Car.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.classification.id !== null) {
                Classification.update(vm.classification, onSaveSuccess, onSaveError);
            } else {
                Classification.save(vm.classification, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('springCarsApp:classificationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
