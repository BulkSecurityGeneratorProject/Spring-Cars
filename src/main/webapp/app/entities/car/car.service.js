(function() {
    'use strict';
    angular
        .module('springCarsApp')
        .factory('Car', Car);

    Car.$inject = ['$resource', 'DateUtils'];

    function Car ($resource, DateUtils) {
        var resourceUrl =  'api/cars/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'byFilter': {method:'GET', isArray: true, url:'api/car/byfilters'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.year = DateUtils.convertDateTimeFromServer(data.year);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
