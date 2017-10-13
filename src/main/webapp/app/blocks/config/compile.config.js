(function() {
    'use strict';

    angular
        .module('riskApp')
        .config(compileServiceConfig);

    compileServiceConfig.$inject = ['$compileProvider','DEBUG_INFO_ENABLED'];

    function compileServiceConfig($compileProvider,DEBUG_INFO_ENABLED) {
        // disable debug data on prod profile to improve performance
        $compileProvider.debugInfoEnabled(DEBUG_INFO_ENABLED);
    }
})();
