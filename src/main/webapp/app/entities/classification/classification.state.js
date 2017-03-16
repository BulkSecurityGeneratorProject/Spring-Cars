(function() {
    'use strict';

    angular
        .module('springCarsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('classification', {
            parent: 'entity',
            url: '/classification',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'springCarsApp.classification.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/classification/classifications.html',
                    controller: 'ClassificationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('classification');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('classification-detail', {
            parent: 'entity',
            url: '/classification/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'springCarsApp.classification.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/classification/classification-detail.html',
                    controller: 'ClassificationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('classification');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Classification', function($stateParams, Classification) {
                    return Classification.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'classification',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('classification-detail.edit', {
            parent: 'classification-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classification/classification-dialog.html',
                    controller: 'ClassificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Classification', function(Classification) {
                            return Classification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('classification.new', {
            parent: 'classification',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classification/classification-dialog.html',
                    controller: 'ClassificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('classification', null, { reload: 'classification' });
                }, function() {
                    $state.go('classification');
                });
            }]
        })
        .state('classification.edit', {
            parent: 'classification',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classification/classification-dialog.html',
                    controller: 'ClassificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Classification', function(Classification) {
                            return Classification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('classification', null, { reload: 'classification' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('classification.delete', {
            parent: 'classification',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/classification/classification-delete-dialog.html',
                    controller: 'ClassificationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Classification', function(Classification) {
                            return Classification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('classification', null, { reload: 'classification' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
