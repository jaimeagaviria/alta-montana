angular.module("myApp")
    .controller("bitacoraController", ['$scope', 'bitacoraServices', 'recetasServices', 'componentesServices', function($scope, bitacoraServices, recetasServices, componentesServices){

        $scope.listarRecetas = function(){
            recetasServices.listarRecetas().then(function (response){
                $scope.recetas = response.data.data;
            }, function (response) {
                alert("Error listando recetas");
            });
        }

        $scope.listarComponentes = function(){
            componentesServices.listarComponentes($scope.receta.recetaID).then(function (response){
                $scope.componentes = response.data.data;
            }, function (response) {
                alert("Error listando componentes");
            });
        }

        $scope.listarRecetas();
    }]);