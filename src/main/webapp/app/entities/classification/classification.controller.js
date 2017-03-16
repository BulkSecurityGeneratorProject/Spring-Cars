(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('ClassificationController', ClassificationController);

    ClassificationController.$inject = ['$scope', '$state', 'Classification'];

    function ClassificationController ($scope, $state, Classification) {
        var vm = this;
        
        vm.classifications = [];

        loadAll();

        function loadAll() {
            Classification.query(function(result) {
                vm.classifications = result;
            });
        }
    }
})();
