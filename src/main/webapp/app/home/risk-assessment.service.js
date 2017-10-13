(function() {
    'use strict';
    angular
        .module('riskApp')
        .factory('RiskAssessment', RiskAssessment);

    RiskAssessment.$inject = ['$resource'];

    function RiskAssessment ($resource) {
        var resourceUrl =  'api/risk-assessment/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: false},
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
