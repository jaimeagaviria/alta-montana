package com.altamontana.service;

import com.altamontana.dto.LoteDto;
import com.altamontana.domain.Bitacora;
import com.altamontana.domain.Componente;
import com.altamontana.domain.Receta;
import com.altamontana.repository.BitacoraRepository;
import com.altamontana.service.impl.AbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BitacoraService extends AbstractServiceImpl<Bitacora, Long> {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Autowired
    public BitacoraService(BitacoraRepository bitacoraRepository) {
        super(bitacoraRepository);
    }

    public List<Bitacora> crearLote(Integer recetaID) {

        Receta receta = recetaService.findOne(recetaID);

        Integer ultimoLote = bitacoraRepository.findUltimoLote();
        if (ultimoLote == null){
            ultimoLote = 0;
        }

        ultimoLote ++;

        for (Componente componente : receta.componentes){
            Bitacora bitacora = new Bitacora();
            bitacora.setNumeroProceso(ultimoLote);
            bitacora.setReceta(receta);
            bitacora.setOrden(componente.getOrden());
            bitacora.setProceso(componente.getProceso());
            bitacora.setComponente(componente);
            bitacora.setCantidadTeorica(componente.getCantidadTeorica());
            bitacora.setUnidadmedida(componente.getUnidadmedida());

            bitacoraRepository.save(bitacora);
        }

        return bitacoraRepository.findByNumeroProcesoOrderByOrden(ultimoLote);
    }

    public List<LoteDto> obtenerUltimosLotes() {
        return bitacoraRepository.obtenerUltimosLotes();
    }
}
