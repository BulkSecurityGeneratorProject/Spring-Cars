(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gas', {
            parent: 'entity',
            url: '/gas',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'springCarsApp.gas.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gas/gases.html',
                    controller: 'GasController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gas');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('gas-detail', {
            parent: 'entity',
            url: '/gas/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'springCarsApp.gas.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gas/gas-detail.html',
                    controller: 'GasDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gas');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Gas', function($stateParams, Gas) {
                    return Gas.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gas',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gas-detail.edit', {
            parent: 'gas-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gas/gas-dialog.html',
                    controller: 'GasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gas', function(Gas) {
                            return Gas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gas.new', {
            parent: 'gas',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gas/gas-dialog.html',
                    controller: 'GasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gas', null, { reload: 'gas' });
                }, function() {
                    $state.go('gas');
                });
            }]
        })
        .state('gas.edit', {
            parent: 'gas',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gas/gas-dialog.html',
                    controller: 'GasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gas', function(Gas) {
                            return Gas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gas', null, { reload: 'gas' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gas.delete', {
            parent: 'gas',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gas/gas-delete-dialog.html',
                    controller: 'GasDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gas', function(Gas) {
                            return Gas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gas', null, { reload: 'gas' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
