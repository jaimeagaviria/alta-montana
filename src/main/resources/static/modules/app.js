angular.module("myApp", ['ngRoute','ui.bootstrap','ngAnimate','ngSanitize'])
    .config(['$routeProvider',
        function($routeProvider) {
            $routeProvider.
            when('/recetas', {
                templateUrl: 'receta.html',
                controller: 'recetasController'
            }).
            when('/bitacora', {
                templateUrl: 'bitacora.html',
                controller: 'bitacoraController'
            });
}]);