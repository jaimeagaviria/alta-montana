package com.altamontana.service;

import com.altamontana.domain.Receta;
import com.altamontana.repository.RecetaRepository;
import com.altamontana.service.impl.AbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecetaService extends AbstractServiceImpl<Receta, Integer> {

    @Autowired
    public RecetaService(RecetaRepository recetaRepository) {
        super(recetaRepository);
    }
}
