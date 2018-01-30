angular.module("myApp")
    .service("recetasServices" , ["$http", function($http){
        var dataService = {};

        dataService.guardarReceta = function (receta){
            return $http.post('/receta/guardar', receta);
        }

        dataService.listarRecetas = function (){
            return $http.get('/recetas');
        }

        /*dataService.eliminarCaracteristica = function (caracteristicaID){
            return $http.delete('/caracteristicas/eliminar/' + caracteristicaID);
        }

        dataService.actualizarFactor = function(factor){
            $http.put('/factores/actualizar', factor);
        }*/

        return dataService;
    }]);