package com.altamontana.controller.api;

import com.altamontana.domain.Bitacora;
import com.altamontana.service.AbstractService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bitacora")
public class BitacoraController extends AbstractRestController<Bitacora,Long> {

    public BitacoraController(AbstractService<Bitacora, Long> genericService) {
        super(genericService);
    }

    @Override
    Class<Bitacora> getTClass() {
        return Bitacora.class;
    }

}
