package com.altamontana.service;

import com.altamontana.domain.Componente;
import com.altamontana.repository.ComponenteRepository;
import com.altamontana.service.impl.AbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponenteService extends AbstractServiceImpl<Componente, Integer> {

    private ComponenteRepository componenteRepository;

    @Autowired
    public ComponenteService(ComponenteRepository componenteRepository) {
        super(componenteRepository);
        this.componenteRepository = componenteRepository;
    }

    public List<Componente> findByReceta(Integer recetaID) {
        return componenteRepository.findByReceta(recetaID);
    }
}
