(function() {
    'use strict';
    angular
        .module('springCarsApp')
        .factory('Traction', Traction);

    Traction.$inject = ['$resource'];

    function Traction ($resource) {
        var resourceUrl =  'api/tractions/:id';

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
