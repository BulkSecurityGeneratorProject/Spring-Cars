(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('ManufacturerDeleteController',ManufacturerDeleteController);

    ManufacturerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Manufacturer'];

    function ManufacturerDeleteController($uibModalInstance, entity, Manufacturer) {
        var vm = this;

        vm.manufacturer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Manufacturer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
