(function() {
    'use strict';
    angular
        .module('springCarsApp')
        .factory('Classification', Classification);

    Classification.$inject = ['$resource'];

    function Classification ($resource) {
        var resourceUrl =  'api/classifications/:id';

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
