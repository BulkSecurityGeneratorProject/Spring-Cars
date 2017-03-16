(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('ManufacturerController', ManufacturerController);

    ManufacturerController.$inject = ['$scope', '$state', 'Manufacturer'];

    function ManufacturerController ($scope, $state, Manufacturer) {
        var vm = this;
        
        vm.manufacturers = [];

        loadAll();

        function loadAll() {
            Manufacturer.query(function(result) {
                vm.manufacturers = result;
            });
        }
    }
})();
