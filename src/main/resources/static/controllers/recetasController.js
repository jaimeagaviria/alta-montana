angular.module("myApp")
    .controller("recetasController", ['$scope', 'recetasServices', function($scope, recetasServices){

        $scope.nuevaRecetaVisible = false;

        $scope.nuevaReceta = function(){
            $scope.nuevaRecetaVisible = true;
        }

        $scope.guardarReceta = function(){
            recetasServices.guardarFactor($scope.receta).then(function(response){
                $scope.listarRecetas();
                alert('Receta guardada correctamente');
            }, function (response){
                alert('Error guardando receta');
            });
            $scope.limpiar();
        }

        $scope.listarRecetas = function(){
            recetasServices.listarRecetas().then(function (response){
                $scope.recetas = response.data.data;
            }, function (response) {
                alert("Error listando");
            });
        }

        $scope.listarRecetas();
    }]);