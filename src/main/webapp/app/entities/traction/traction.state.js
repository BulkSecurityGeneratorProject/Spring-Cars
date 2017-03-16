(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('traction', {
            parent: 'entity',
            url: '/traction',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'springCarsApp.traction.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/traction/tractions.html',
                    controller: 'TractionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traction');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('traction-detail', {
            parent: 'entity',
            url: '/traction/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'springCarsApp.traction.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/traction/traction-detail.html',
                    controller: 'TractionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traction');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Traction', function($stateParams, Traction) {
                    return Traction.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'traction',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('traction-detail.edit', {
            parent: 'traction-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/traction/traction-dialog.html',
                    controller: 'TractionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Traction', function(Traction) {
                            return Traction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('traction.new', {
            parent: 'traction',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/traction/traction-dialog.html',
                    controller: 'TractionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                ref: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('traction', null, { reload: 'traction' });
                }, function() {
                    $state.go('traction');
                });
            }]
        })
        .state('traction.edit', {
            parent: 'traction',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/traction/traction-dialog.html',
                    controller: 'TractionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Traction', function(Traction) {
                            return Traction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('traction', null, { reload: 'traction' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('traction.delete', {
            parent: 'traction',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/traction/traction-delete-dialog.html',
                    controller: 'TractionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Traction', function(Traction) {
                            return Traction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('traction', null, { reload: 'traction' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
