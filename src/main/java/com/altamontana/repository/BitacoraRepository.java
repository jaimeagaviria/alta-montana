package com.altamontana.repository;

import com.altamontana.domain.Bitacora;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BitacoraRepository extends AbstractRepository<Bitacora, Long> {

    @Query("SELECT MAX(x.numeroProceso) FROM Bitacora x")
    Integer findUltimoLote();

    List<Bitacora> findByNumeroProcesoOrderByOrden(Integer ultimoLote);
}
