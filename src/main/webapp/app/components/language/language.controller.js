(function() {
    'use strict';

    angular
        .module('riskApp')
        .controller('LanguageController', LanguageController);

    LanguageController.$inject = ['$translate', 'LanguageService', 'tmhDynamicLocale'];

    function LanguageController ($translate, LanguageService, tmhDynamicLocale) {
        var vm = this;

        vm.changeLanguage = changeLanguage;
        vm.languages = null;

        LanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

        function changeLanguage (languageKey) {
            $translate.use(languageKey);
            tmhDynamicLocale.set(languageKey);
        }
    }
})();
