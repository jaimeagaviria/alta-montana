angular.module("myApp")
    .controller("recetasController", ['$scope', 'recetasServices', 'componentesServices', function($scope, recetasServices, componentesServices){

        $scope.nuevaRecetaVisible = false;
        $scope.nuevoComponenteVisible = false;

        $scope.nuevaReceta = function(){
            $scope.receta = {};
            $scope.nuevaRecetaVisible = true;
        }

        $scope.seleccionarReceta = function(receta){
            $scope.nuevaRecetaVisible = true;
            $scope.nuevoComponenteVisible = false;
            $scope.receta = receta;
            $scope.listarComponentes();
            $scope.componente = {};
        }

        $scope.nuevoComponente = function(){
            $scope.nuevoComponenteVisible = true;
        }

        $scope.guardarReceta = function(){
            recetasServices.guardarReceta($scope.receta).then(function(response){
                $scope.listarRecetas();
                $scope.receta = {};
                $scope.nuevaRecetaVisible = false;
                alert('Receta guardada correctamente');
            }, function (response){
                alert('Error guardando receta');
            });
        }

        $scope.listarRecetas = function(){
            recetasServices.listarRecetas().then(function (response){
                $scope.recetas = response.data.data;
            }, function (response) {
                alert("Error listando");
            });
        }

        $scope.listarComponentes = function(){
            componentesServices.listarComponentes($scope.receta.recetaID).then(function (response){
                $scope.componentes = response.data.data;
            }, function (response) {
                alert("Error listando");
            });
        }

        $scope.guardarComponente = function(){
            if ($scope.componente.componenteID){
                componentesServices.actualizarComponente($scope.componente).then(function(response){
                    $scope.listarComponentes();
                    $scope.componente = {};
                    $scope.nuevoComponenteVisible = false;
                    alert('Componente actualizado correctamente');
                }, function (response){
                    alert('Error actualizando componente');
                });
            }else{
                $scope.componente.receta = $scope.receta;
                componentesServices.guardarComponente($scope.componente).then(function(response){
                    $scope.listarComponentes();
                    $scope.componente = {};
                    $scope.nuevoComponenteVisible = false;
                    alert('Componente guardado correctamente');
                }, function (response){
                    alert('Error guardando componente');
                });
            }
        }

        $scope.cancelarComponente = function(){
            $scope.nuevoComponenteVisible = false;
            $scope.componente = {};
        }

        $scope.eliminarComponente = function(componenteID){
            componentesServices.eliminarComponente(componenteID).then(function(response){
                $scope.listarComponentes();
                alert('Componente eliminado correctamente');
            }, function (response){
                alert('Error guardando componente');
            });
        }

        $scope.editarComponente = function(componente){
            $scope.componente = angular.copy(componente);
            $scope.nuevoComponenteVisible = true;
        }

        $scope.listarRecetas();
    }]);