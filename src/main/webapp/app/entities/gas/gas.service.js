(function() {
    'use strict';
    angular
        .module('springCarsApp')
        .factory('Gas', Gas);

    Gas.$inject = ['$resource'];

    function Gas ($resource) {
        var resourceUrl =  'api/gases/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
