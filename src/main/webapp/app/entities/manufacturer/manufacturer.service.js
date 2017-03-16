(function() {
    'use strict';
    angular
        .module('springCarsApp')
        .factory('Manufacturer', Manufacturer);

    Manufacturer.$inject = ['$resource', 'DateUtils'];

    function Manufacturer ($resource, DateUtils) {
        var resourceUrl =  'api/manufacturers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.yearFund = DateUtils.convertDateTimeFromServer(data.yearFund);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
