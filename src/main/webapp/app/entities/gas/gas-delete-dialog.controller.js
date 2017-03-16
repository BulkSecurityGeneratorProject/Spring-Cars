(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('GasDeleteController',GasDeleteController);

    GasDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gas'];

    function GasDeleteController($uibModalInstance, entity, Gas) {
        var vm = this;

        vm.gas = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Gas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
