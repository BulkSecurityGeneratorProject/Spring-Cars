(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('TractionDeleteController',TractionDeleteController);

    TractionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Traction'];

    function TractionDeleteController($uibModalInstance, entity, Traction) {
        var vm = this;

        vm.traction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Traction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
