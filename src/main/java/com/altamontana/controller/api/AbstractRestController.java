package com.altamontana.controller.api;

import com.altamontana.common.exception.ValidacionException;
import com.altamontana.common.rest.RestResponse;
import com.altamontana.service.AbstractService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaviriaj on 28/07/16.
 */

@RestController
public abstract class AbstractRestController<E, K extends Serializable> extends AbstractController {

    protected AbstractService<E, K> genericService;

    public AbstractRestController(AbstractService<E, K> genericService) {
        this.genericService = genericService;
    }

    abstract Class<E> getTClass();

    /**
     * Lista todos los registros usando el metodo GET
     * Ejemplo ajax Like: url: encodeURI("${pageContext.request.contextPath}/controllerName/?filter=field1 like '%value1%'"),
     *
     * @param filter
     * @param limit
     * @param offset
     * @param sort
     * @param request
     * @return
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse> get(@RequestParam(required = false) String[] filter,
                                            @RequestParam(required = false) Integer limit,
                                            @RequestParam(required = false) Integer offset,
                                            @RequestParam(required = false) String[] sort,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {

        response.setBufferSize(800 * 1024);
        RestResponse restResponse;

        if (debug) LOGGER.debug("GET - {} Parametros: {}", request.getServletPath(), request.getQueryString());

        try {
            List<E> all;
            all = this.genericService.findAll(filter, limit, offset, sort);

            if (all == null || all.isEmpty()) {
                if (debug) LOGGER.debug("La consulta {} no retorno valores", request.getServletPath());

                restResponse = createResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.name(), null, request);
                return new ResponseEntity<>(restResponse, HttpStatus.NO_CONTENT);
            } else {
                restResponse = createResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), all, request);

                Long rows = this.genericService.count(filter);
                if (debug) LOGGER.debug("La consulta {} retorno {} registros", request.getServletPath(), rows);

                restResponse.setRows(rows);

                return new ResponseEntity<>(restResponse, HttpStatus.OK);
            }
        } catch (Exception ex) {
            String mensaje = String.format("%s %s %s", HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), ex.getCause());
            LOGGER.error(mensaje, ex);

            restResponse = createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), mensaje, null, request);
            return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***
     * Obtiene un registro por el id usando el metodo GET
     * @param id
     * @param request
     * @return
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse> get(@PathVariable("id") K id,
                                            HttpServletRequest request) {

        RestResponse restResponse = new RestResponse();

        if (debug) LOGGER.debug("GET - {} Id: {} Parametros: {}", request.getServletPath(), id, request.getQueryString());

        try {
            E all = this.genericService.findOne(id);
            restResponse.setData(all);

            if (all == null) {
                restResponse = createResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.name(), null, request);
            } else {
                restResponse = createResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), all, request);
            }
            return new ResponseEntity<>(restResponse, HttpStatus.OK);
        } catch (Exception ex) {
            String mensaje = String.format("%s %s %s", HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), ex.getCause());
            LOGGER.error(mensaje);

            restResponse = createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),mensaje, ex, null);
            return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /***
     * Modifica un nuevo registro usando el metodo PUT
     * @param object
     * @param request
     * @return
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse> put(@RequestBody E object,
                                            HttpServletRequest request) {

        if (debug) LOGGER.debug("PUT - {} Parametros: {}", request.getServletPath(), request.getQueryString());

        return doSave(object, request);
    }

    /***
     * Modifica el registro usando el metodo PATCH
     * Este metodo solo modifica los campos a los que se les asigna algun valor, no funciona para valores null por que son omitidos.
     * @param id
     * @param entity
     * @param request
     * @return
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse> patch(@PathVariable("id") K id,
                                              @RequestBody E entity,
                                              HttpServletRequest request) {

        if (debug) LOGGER.debug("PATCH - {} Parametros: {}", request.getServletPath(), request.getQueryString());

        entity = this.genericService.patch(id, entity);
        return guardarEntidad(entity, request);
    }

    /***
     * Crea un nuevo registro usando el metodo POST
     * @param entity
     * @param request
     * @return
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse> post(@RequestBody E entity,
                                             HttpServletRequest request) {

        if (debug) LOGGER.debug("POST - {} Parametros: {} Objeto: {}", request.getServletPath(), request.getQueryString(), gsonBean.toJson(entity));
        return doSave(entity, request);
    }

    /**
     * Elimina un registro usando el metodo DELETE
     *
     * @param id
     * @param request
     * @return
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse> delete(@PathVariable("id") K id,
                                               HttpServletRequest request) {

        RestResponse restResponse;

        if (debug) LOGGER.debug("DELETE - {} Parametros: {}", request.getServletPath(), request.getQueryString());

        try {
            boolean respuesta = this.genericService.deleteById(id);
            if (respuesta) {
                restResponse = createResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), id, request);
                return new ResponseEntity<>(restResponse, HttpStatus.OK);
            } else {
                restResponse = createResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), id, request);
                return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException ex) {
            String mensaje = String.format("Error Exception en: DELETE - %s Parametros: %s ID: %s", request.getRequestURI(), request.getQueryString(), id);

            LOGGER.error(mensaje, ex);
            restResponse =  createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "No se puede eliminar el registro por que tiene información relacionada.", null, request);
            return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            String mensaje = String.format("Error Exception en: DELETE - %s Parametros: %s ID: %s", request.getRequestURI(), request.getQueryString(), id);

            LOGGER.error(mensaje, ex);
            restResponse =  createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), mensaje, null, request);
            return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    protected ResponseEntity<RestResponse> doSave(E entity, HttpServletRequest request) {
        return guardarEntidad(entity, request);
    }

    private ResponseEntity<RestResponse> procesarJsonToBeanException(HttpServletRequest request, String json, E entity) {
        String mensaje = String.format("No se puede convertir %s al objeto %s", json, getTClass().getName());

        List<String> errores = new ArrayList<>();
        errores.add(mensaje);
        RestResponse restResponse = createResponse(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), entity, request, errores);
        return new ResponseEntity<>(restResponse, HttpStatus.CONFLICT);
    }

    private ResponseEntity<RestResponse> guardarEntidad(@RequestBody @Valid E entity, HttpServletRequest request) {
        RestResponse restResponse;
        E newEntity;
        List<String> errores;
        try {
            newEntity = this.genericService.save(entity);
            //if (debug) LOGGER.debug("Registro guardado {}", gsonBean.toJson(newEntity));
        }catch (DataIntegrityViolationException ex) {
            LOGGER.error(String.format(GENERAL_ERROR, request.getRequestURI(), request.getQueryString(), ex));

            errores = new ArrayList<>();
            errores.add(ex.getRootCause().getMessage());

            restResponse = createResponse(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), entity, request, errores);
            return new ResponseEntity<>(restResponse, HttpStatus.CONFLICT);

        } catch (ConstraintViolationException ex) {
            LOGGER.error(String.format(GENERAL_ERROR, request.getRequestURI(), request.getQueryString()), ex);

            errores = procesarListaErrores(ex);
            restResponse = createResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), entity, request, errores);
            return new ResponseEntity<>(restResponse, HttpStatus.CONFLICT);

        }catch (ValidacionException ex) {
            LOGGER.warn(String.format(GENERAL_ERROR, request.getRequestURI(), request.getQueryString()), ex);

            errores = new ArrayList<>();
            errores.add(ex.getMessage());

            restResponse = createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), entity, request, errores);
            return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            LOGGER.error(String.format(GENERAL_ERROR, request.getRequestURI(), request.getQueryString()), ex);

            errores = new ArrayList<>();
            errores.add(ex.getCause().getMessage());

            restResponse = createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), entity, request, errores);
            return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (newEntity != null) {
            restResponse = createResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(), newEntity, request);
            return new ResponseEntity<>(restResponse, HttpStatus.CREATED);
        } else {
            restResponse = createResponse(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), null, request);
            return new ResponseEntity<>(restResponse, HttpStatus.CONFLICT);
        }
    }
}
