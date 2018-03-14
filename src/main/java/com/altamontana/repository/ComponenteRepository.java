package com.altamontana.repository;

import com.altamontana.domain.Componente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComponenteRepository extends AbstractRepository<Componente, Integer> {
    @Query("SELECT c FROM Componente c WHERE c.receta.recetaID=:recetaID")
    List<Componente> findByReceta(@Param("recetaID") Integer recetaID);
}
