(function() {
    'use strict';

    angular
        .module('riskApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state'];

    function NavbarController ($state) {
        var vm = this;

        vm.$state = $state;

    }
})();
