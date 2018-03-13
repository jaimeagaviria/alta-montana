package com.altamontana.controller.api;

import com.altamontana.domain.Componente;
import com.altamontana.service.ComponenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/componentes")
public class ComponenteController extends AbstractRestController<Componente,Integer>{

    @Autowired
    public ComponenteController(ComponenteService componenteService) {
        super(componenteService);
    }

    @Override
    Class<Componente> getTClass() {
        return Componente.class;
    }
}
