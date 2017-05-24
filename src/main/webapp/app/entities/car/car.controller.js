(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .controller('CarController', CarController);

    CarController.$inject = ['$scope', '$state', 'Car'];

    function CarController ($scope, $state, Car) {
        var vm = this;
        var manufacturer ="";
        vm.cars = [];
        vm.manufacturers = [];
        vm.segments = [];
        vm.filter = {};
        vm.reset = function(){
            vm.filter = {};
            loadAll();
        }
        vm.searchByFilter =  function (){
            if(!vm.filter.manufacturer){
                manufacturer = "";
            }
            else{
                manufacturer = vm.filter.manufacturer.name;
            }
            Car.byFilter({
              model: vm.filter.model,
              sales: vm.filter.sales,
              manufacturer: manufacturer,
              minPrice: vm.filter.minPrice,
              maxPrice: vm.filter.maxPrice,
              segment : vm.filter.segment
            },onSuccess,onError);
        };
        function onSuccess(data,headers){
            vm.cars = data;
        }
        function onError(){
            console.log("something went wrong! :(");
        }
        loadAll();

        function loadAll() {
            Car.query(function(result) {
                vm.cars = result;
                vm.manufacturers = [];
                vm.segments = [];
                angular.forEach(result, function (x,y) {
                        vm.manufacturers.push(x.manufacturer);
                        vm.segments.push(x.segment);
                });
            });
        }
    }
})();
