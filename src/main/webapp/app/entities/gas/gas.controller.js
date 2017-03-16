(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('GasController', GasController);

    GasController.$inject = ['$scope', '$state', 'Gas'];

    function GasController ($scope, $state, Gas) {
        var vm = this;
        
        vm.gases = [];

        loadAll();

        function loadAll() {
            Gas.query(function(result) {
                vm.gases = result;
            });
        }
    }
})();
