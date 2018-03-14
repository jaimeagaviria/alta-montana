package com.altamontana.service;

import com.altamontana.domain.Componente;
import com.altamontana.domain.Receta;
import com.altamontana.repository.RecetaRepository;
import com.altamontana.service.impl.AbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class RecetaService extends AbstractServiceImpl<Receta, Integer> {

    private RecetaRepository recetaRepository;

    @Autowired
    private ComponenteService componenteService;

    @Autowired
    public RecetaService(RecetaRepository recetaRepository) {
        super(recetaRepository);
        this.recetaRepository = recetaRepository;
    }

    @Transactional(readOnly = false)
    public Receta duplicarReceta(Integer recetaID, String nombre) {
        Receta receta = recetaRepository.findOne(recetaID);
        Receta nuevaReceta = new Receta();

        nuevaReceta.setNombre(nombre);
        nuevaReceta.setDensidadInicial(receta.getDensidadInicial());
        nuevaReceta.setDensidadFinal(receta.getDensidadFinal());
        recetaRepository.save(nuevaReceta);

        List<Componente> componentes = componenteService.findByReceta(recetaID);
        for (Componente componente: componentes){
            Componente nuevoComponente = new Componente();
            nuevoComponente.setReceta(nuevaReceta);
            nuevoComponente.setCantidadTeorica(componente.getCantidadTeorica());
            nuevoComponente.setNombreComponente(componente.getNombreComponente());
            nuevoComponente.setOrden(componente.getOrden());
            nuevoComponente.setProceso(componente.getProceso());
            nuevoComponente.setUnidadmedida(componente.getUnidadmedida());
            componenteService.save(nuevoComponente);
        }
        return nuevaReceta;
    }
}
