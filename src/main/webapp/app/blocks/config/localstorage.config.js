(function() {
    'use strict';

    angular
        .module('riskApp')
        .config(localStorageConfig);

    localStorageConfig.$inject = ['$localStorageProvider', '$sessionStorageProvider'];

    function localStorageConfig($localStorageProvider, $sessionStorageProvider) {
        $localStorageProvider.setKeyPrefix('risk-');
        $sessionStorageProvider.setKeyPrefix('risk-');
    }
})();
