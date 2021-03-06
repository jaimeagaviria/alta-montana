package com.altamontana.controller.api;

import com.altamontana.dto.LoteDto;
import com.altamontana.common.rest.RestResponse;
import com.altamontana.domain.Bitacora;
import com.altamontana.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bitacora")
public class BitacoraController extends AbstractRestController<Bitacora,Long> {

    private BitacoraService bitacoraService;

    @Autowired
    public BitacoraController(BitacoraService bitacoraService) {
        super(bitacoraService);
        this.bitacoraService = bitacoraService;
    }

    @Override
    Class<Bitacora> getTClass() {
        return Bitacora.class;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "crearLote/{recetaID}", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse> crearLote(@PathVariable("recetaID") Integer recetaID) {

        try {
            List<Bitacora> bitacoraList = bitacoraService.crearLote(recetaID);

            RestResponse restResponseDto = new RestResponse();
            restResponseDto.setStatus(HttpStatus.OK.value());
            restResponseDto.setMessage(HttpStatus.OK.name());
            restResponseDto.setData(bitacoraList);
            restResponseDto.setErrors(null);

            return new ResponseEntity<>(restResponseDto, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "ultimosLotes/{cantidad}", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse> ultimosLotes(@PathVariable("cantidad") Integer cantidad) {
        try {
            List<LoteDto> ultimosLotes = bitacoraService.obtenerUltimosLotes();

            RestResponse restResponseDto = new RestResponse();
            restResponseDto.setStatus(HttpStatus.OK.value());
            restResponseDto.setMessage(HttpStatus.OK.name());
            restResponseDto.setData(ultimosLotes);
            restResponseDto.setErrors(null);

            return new ResponseEntity<>(restResponseDto, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "eliminarLote/{lote}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse> eliminarLote(@PathVariable("lote") Integer lote) {
        try {
            bitacoraService.eliminarLote(lote);

            RestResponse restResponseDto = new RestResponse();
            restResponseDto.setStatus(HttpStatus.OK.value());
            restResponseDto.setMessage(HttpStatus.OK.name());
            restResponseDto.setData(null);
            restResponseDto.setErrors(null);

            return new ResponseEntity<>(restResponseDto, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
