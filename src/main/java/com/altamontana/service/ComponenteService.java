package com.altamontana.service;

import com.altamontana.domain.Componente;
import com.altamontana.repository.ComponenteRepository;
import com.altamontana.service.impl.AbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComponenteService extends AbstractServiceImpl<Componente, Integer> {

    @Autowired
    public ComponenteService(ComponenteRepository componenteRepository) {
        super(componenteRepository);
    }
}
