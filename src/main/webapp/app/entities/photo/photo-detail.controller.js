(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('PhotoDetailController', PhotoDetailController);

    PhotoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Photo', 'Car'];

    function PhotoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Photo, Car) {
        var vm = this;

        vm.photo = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('springCarsApp:photoUpdate', function(event, result) {
            vm.photo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
