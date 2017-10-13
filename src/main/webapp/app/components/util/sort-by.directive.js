(function() {
    'use strict';

    angular
        .module('riskApp')
        .directive('sortBy', sortBy);

    function sortBy() {
        var directive = {
            restrict: 'A',
            scope: false,
            require: '^sort',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs, parentCtrl) {
            element.bind('click', function () {
                parentCtrl.sort(attrs.sortBy);
            });
        }
    }
})();
