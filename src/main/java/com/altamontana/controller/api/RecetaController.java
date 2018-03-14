package com.altamontana.controller.api;

import com.altamontana.common.rest.RestResponse;
import com.altamontana.domain.Bitacora;
import com.altamontana.domain.Componente;
import com.altamontana.domain.Receta;
import com.altamontana.service.AbstractService;
import com.altamontana.service.ComponenteService;
import com.altamontana.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/recetas")
public class RecetaController extends AbstractRestController<Receta,Integer>{

    private RecetaService recetaService;

    @Autowired
    public RecetaController(RecetaService recetaService) {
        super(recetaService);
        this.recetaService = recetaService;
    }

    @Override
    Class<Receta> getTClass() {
        return Receta.class;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "duplicar/{recetaID}/{nombre}", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse> crearLote(@PathVariable("recetaID") Integer recetaID,
                                                  @PathVariable("nombre") String nombre,
                                                  HttpServletRequest request) {

        try {
            Receta nuevaReceta = recetaService.duplicarReceta(recetaID, nombre);

            RestResponse restResponseDto = new RestResponse();
            restResponseDto.setStatus(HttpStatus.OK.value());
            restResponseDto.setMessage(HttpStatus.OK.name());
            restResponseDto.setData(nuevaReceta);
            restResponseDto.setErrors(null);

            return new ResponseEntity<RestResponse>(restResponseDto, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<RestResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
