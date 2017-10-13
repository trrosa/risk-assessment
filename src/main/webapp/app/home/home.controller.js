(function () {
  'use strict';

  angular
          .module('riskApp')
          .controller('HomeController', HomeController);

  HomeController.$inject = ['RiskAssessment', 'paginationConstants', '$state', 'pagingParams', '$filter'];

  function HomeController(RiskAssessment, paginationConstants, $state, pagingParams, $filter) {
    var vm = this;
    vm.riskAssessment = null;

    vm.riskAssessments = [];
    vm.loadPage = loadPage;
    vm.itemsPerPage = paginationConstants.itemsPerPage;
    vm.predicate = pagingParams.predicate;
    vm.reverse = pagingParams.ascending;
    vm.clear = clear;
    vm.loadAll = loadAll;
    vm.transition = transition;

    loadAll();

    vm.cancel = function () {
      vm.riskAssessment = null;
      angular.element('#editForm').controller("form").$setPristine();
    };

    vm.save = function () {
      vm.isSaving = true;
      if (!vm.riskAssessment.id) {
        RiskAssessment.save(vm.riskAssessment, onSaveSuccess, onError);
      } else {
        RiskAssessment.update(vm.riskAssessment, onSaveSuccess, onError);
      }
    };

    vm.edit = function (risk) {
      vm.riskAssessment = angular.copy(risk);
    };

    vm.delete = function (risk) {
      swal({
        title: $filter('translate')("entity.action.delete"),
        text: $filter('translate')("home.excluir", "{id: " + risk.id + "}"),
        type: 'warning',
        showCancelButton: true,
        closeOnConfirm: false
      }, function (isConfirm) {
        if (isConfirm) {
          RiskAssessment.delete({id: risk.id}).$promise.then(function (f) {

            swal({
              title: $filter('translate')("entity.action.delete"),
              message: $filter('translate')("home.excluido"),
              type: "success"
            }, loadAll);
          }, onError);
        }
      });
    };

    function onSaveSuccess(data) {
      vm.isSaving = false;
      vm.cancel();
      loadAll();
    }

    function onError(e) {
      vm.isSaving = false;
      swal(
              $filter('translate')("error.title"),
              $filter('translate')("error.internalServerError"),
              "error");
    }

    function transition() {
      $state.transitionTo($state.$current, {
        page: vm.page,
        sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')
      });
    }

    function loadAll() {
      RiskAssessment.query({
        page: pagingParams.page - 1,
        size: vm.itemsPerPage,
        sort: sort()
      }, onSuccess, onError);
      function sort() {
        var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
        if (vm.predicate !== 'id') {
          result.push('id');
        }
        return result;
      }
      function onSuccess(data, headers) {
        vm.totalItems = data.totalElements;
        vm.riskAssessments = data.content;
        vm.page = pagingParams.page;
      }
    }

    function loadPage(page) {
      vm.page = page;
      vm.transition();
    }

    function transition() {
      $state.transitionTo($state.$current, {
        page: vm.page,
        sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')
      });
    }

    function search(searchQuery) {
      if (!searchQuery) {
        return vm.clear();
      }
      vm.page = 1;
      vm.predicate = '_score';
      vm.reverse = false;
      vm.transition();
    }

    function clear() {
      vm.page = 1;
      vm.predicate = 'id';
      vm.reverse = true;
      vm.transition();
    }

  }
})();
