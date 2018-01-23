package com.altamontana.controller.api;

import com.altamontana.common.rest.RestResponse;
import com.altamontana.common.util.DateManager;
import com.google.gson.Gson;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaviriaj on 21/01/2017.
 */
public abstract class AbstractController {

    protected static final String GENERAL_ERROR                         = "Error en: %s Parametros: %s";
    protected static final String DULICATE_KEY                          = "duplicate key";
    protected static final String REGISTRO_DUPLICADO                    = "Registro duplicado";

    protected static final String API_TOKEN_DESCRIPCION                 = "Token de seguridad otorgado por el servicio de autenticación, si no tiene un token utilice el servicio login.";
    protected static final String API_TENANT_DESCRIPCION                = "Indica el nombre del tenant ó base de datos a la que se conectará el backend";
    protected static final String API_X_CONTENT_DESCRIPCION             = "Indica la forma en que el backend interpretará los datos (json ó encriptado), si se usa la palabra json interpretará el contenido en json, si se envia safe interpretará el contenido de forma encriptada";
    protected static final String API_X_MODE_DESCRIPCION                = "Indica si este servicio se ejecuta en modo pruebas ó en modo producción, prod=produccion y test=pruebas";

    protected static final String API_ID_DESCRIPCION                    = "Identificador del registro";
    protected static final String API_FILTER_DESCRIPCION                = "Usado para filtrar registros, en este parámetro debe usar el nombre del campo de la entidad y no el nombre del campo de la tabla, los filtros van separados por coma (,) y el operador usado es el AND.";
    protected static final String API_LIMIT_DESCRIPCION                 = "Usado para limitar la candidad de registros retornados por el backend.";
    protected static final String API_OFFSET_DESCRIPCION                = "Usado para establecer la posición inicial de lectura de registros, puede combinarlo con el parametro limit para paginar registos.";
    protected static final String API_SORT_DESCRIPCION                  = "Usado para establecer el orden de los registros retornados por el backend, los campos van separados por coma (,) y se puede usar las palabras ASC y DESC.";

    protected static final String API_NOT_FOUND_DESCRIPCION             = "No se encontró información, no existen registros, si esta usando el parámetro [filters] es posible que no existan registros para ese tipo de filtro.";
    protected static final String API_INTERNAL_SERVER_ERROR_DESCRIPCION = "Ocurrio una excepción en el sistema, la variable [message] y [errors] pueden ayudarle a interpretar el error, para este caso la variable [data] siempre trae null.";
    protected static final String API_UNAUTHORIZED_DESCRIPCION          = "El token de seguridad proporcionado no es válido, utilice el servicio autenticar para obtener un token valido.";
    protected static final String API_FORBIDDEN_DESCRIPCION             = "El token de seguridad es válido, pero no cuenta con permisos para ejecutar esta operación";
    protected static final String API_CONFLICT_DESCRIPCION              = "No se insertó el registro por inconsistencias ó errores de validación, la variable [message] y [errors] pueden ayudarle a interpretar el error.";
    protected static final String API_CODIGO_NO_USADO_DESCRIPCION       = "Este código de respuesta no es usando en este servicio.";

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    protected final boolean debug = LOGGER.isDebugEnabled();
    protected final boolean info = LOGGER.isInfoEnabled();

    private static final int LENGTH_SHA256 = 32;

    @Autowired
    protected Gson gsonBean;

    @Autowired
    protected DateManager dateManager;

    /**
     * Método genérico para devolver respuesta de tipo error 500 en el caso de las excepciones
     */
    protected <E> RestResponse<E> createResponse(Exception ex, HttpServletRequest request) {
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null, request);
    }

    protected <E> RestResponse<E> createResponse(HttpStatus status, E data, HttpServletRequest request) {
        return createResponse(status.value(), status.name(), data, request);
    }

    protected <E> RestResponse<E> createResponse(int statusCode, String mensaje, E data, HttpServletRequest request){
        return createResponse(statusCode, mensaje, data, request, null);
    }

    protected <E> RestResponse<E>  createResponse(int statusCode, String mensaje, Object data, HttpServletRequest request, List<String> errores){
        RestResponse restResponseDto = new RestResponse();
        restResponseDto.setStatus(statusCode);
        restResponseDto.setMessage(mensaje);
        restResponseDto.setData(data);
        restResponseDto.setErrors(errores);
        return restResponseDto;
    }

    protected List<String> procesarListaErrores(ConstraintViolationException ex) {
        List<String> errores;
        errores = new ArrayList<>();
        for (ConstraintViolation constraintViolation: ex.getConstraintViolations()){
            errores.add(String.format("El campo %s %s", constraintViolation.getPropertyPath(), ((ConstraintViolationImpl) constraintViolation).getMessage()));
        }
        return errores;
    }

}
