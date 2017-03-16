(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('LinkDetailController', LinkDetailController);

    LinkDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Link', 'Car'];

    function LinkDetailController($scope, $rootScope, $stateParams, previousState, entity, Link, Car) {
        var vm = this;

        vm.link = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('springCarsApp:linkUpdate', function(event, result) {
            vm.link = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
