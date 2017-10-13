(function () {
  'use strict';

  angular
          .module('riskApp')
          .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider.state('home', {
      parent: 'app',
      url: '/?page&sort',
      data: {
        authorities: []
      },
      params: {
        page: {
          value: '1',
          squash: true
        },
        sort: {
          value: 'id,asc',
          squash: true
        }
      },
      views: {
        'content@': {
          templateUrl: 'app/home/home.html',
          controller: 'HomeController',
          controllerAs: 'vm'
        }
      },
      resolve: {
        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
            $translatePartialLoader.addPart('home');
            $translatePartialLoader.addPart('risk_type');
            return $translate.refresh();
          }],
        pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
            return {
              page: PaginationUtil.parsePage($stateParams.page),
              sort: $stateParams.sort,
              predicate: PaginationUtil.parsePredicate($stateParams.sort),
              ascending: PaginationUtil.parseAscending($stateParams.sort)
            };
          }]
      }
    });
  }
})();
