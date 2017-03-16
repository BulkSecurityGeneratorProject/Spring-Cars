(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('CarDetailController', CarDetailController);

    CarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Car', 'Manufacturer', 'Link', 'Classification', 'Traction', 'Gas', 'Photo'];

    function CarDetailController($scope, $rootScope, $stateParams, previousState, entity, Car, Manufacturer, Link, Classification, Traction, Gas, Photo) {
        var vm = this;

        vm.car = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('springCarsApp:carUpdate', function(event, result) {
            vm.car = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
