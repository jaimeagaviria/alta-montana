package com.altamontana.service;

import com.altamontana.domain.Bitacora;
import com.altamontana.repository.BitacoraRepository;
import com.altamontana.service.impl.AbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BitacoraService extends AbstractServiceImpl<Bitacora, Long> {

    @Autowired
    public BitacoraService(BitacoraRepository bitacoraRepository) {
        super(bitacoraRepository);
    }
}
