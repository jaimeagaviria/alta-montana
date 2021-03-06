package com.altamontana.repository;

import com.altamontana.dto.LoteDto;
import com.altamontana.domain.Bitacora;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BitacoraRepository extends AbstractRepository<Bitacora, Long> {

    @Query("SELECT MAX(x.numeroProceso) FROM Bitacora x")
    Integer findUltimoLote();

    List<Bitacora> findByNumeroProcesoOrderByOrden(Integer ultimoLote);

    @Query("SELECT DISTINCT new com.altamontana.dto.LoteDto (b.numeroProceso, b.receta.nombre) FROM Bitacora b ORDER BY b.numeroProceso DESC")
    List<LoteDto> obtenerUltimosLotes();

    @Modifying
    @Query("DELETE FROM Bitacora b WHERE b.numeroProceso= :lote")
    void eliminarLote(@Param("lote") Integer lote);
}
