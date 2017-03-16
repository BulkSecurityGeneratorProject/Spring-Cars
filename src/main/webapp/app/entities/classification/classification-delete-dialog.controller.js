(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('ClassificationDeleteController',ClassificationDeleteController);

    ClassificationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Classification'];

    function ClassificationDeleteController($uibModalInstance, entity, Classification) {
        var vm = this;

        vm.classification = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Classification.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
