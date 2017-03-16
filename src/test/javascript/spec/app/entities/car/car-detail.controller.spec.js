'use strict';

describe('Controller Tests', function() {

    describe('Car Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCar, MockManufacturer, MockLink, MockClassification, MockTraction, MockGas, MockPhoto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCar = jasmine.createSpy('MockCar');
            MockManufacturer = jasmine.createSpy('MockManufacturer');
            MockLink = jasmine.createSpy('MockLink');
            MockClassification = jasmine.createSpy('MockClassification');
            MockTraction = jasmine.createSpy('MockTraction');
            MockGas = jasmine.createSpy('MockGas');
            MockPhoto = jasmine.createSpy('MockPhoto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Car': MockCar,
                'Manufacturer': MockManufacturer,
                'Link': MockLink,
                'Classification': MockClassification,
                'Traction': MockTraction,
                'Gas': MockGas,
                'Photo': MockPhoto
            };
            createController = function() {
                $injector.get('$controller')("CarDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'springCarsApp:carUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
