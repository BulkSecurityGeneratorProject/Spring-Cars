(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('TractionController', TractionController);

    TractionController.$inject = ['$scope', '$state', 'Traction'];

    function TractionController ($scope, $state, Traction) {
        var vm = this;
        
        vm.tractions = [];

        loadAll();

        function loadAll() {
            Traction.query(function(result) {
                vm.tractions = result;
            });
        }
    }
})();
