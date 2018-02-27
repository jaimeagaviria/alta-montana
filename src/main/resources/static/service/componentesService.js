angular.module("myApp")
    .service("componentesServices" , ["$http", function($http){
        var dataService = {};

        dataService.guardarComponente = function (componente){
            return $http.post('/componentes', componente);
        }

        dataService.listarComponentes = function (recetaID){
            return $http.get('/componentes?sort=orden&filter=receta.recetaID=' + recetaID);
        }

        dataService.eliminarComponente = function (componenteID){
            return $http.delete('/componentes/' + componenteID);
         }

         dataService.actualizarComponente = function(componente){
            return $http.put('/componentes', componente);
         }

        return dataService;
    }]);