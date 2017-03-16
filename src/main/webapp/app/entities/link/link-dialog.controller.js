(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('LinkDialogController', LinkDialogController);

    LinkDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Link', 'Car'];

    function LinkDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Link, Car) {
        var vm = this;

        vm.link = entity;
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
            if (vm.link.id !== null) {
                Link.update(vm.link, onSaveSuccess, onSaveError);
            } else {
                Link.save(vm.link, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('springCarsApp:linkUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
