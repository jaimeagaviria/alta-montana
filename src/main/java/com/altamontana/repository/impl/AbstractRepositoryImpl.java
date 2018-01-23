package com.altamontana.repository.impl;

import com.altamontana.common.Constantes;
import com.altamontana.common.util.DateManager;
import com.altamontana.repository.AbstractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by gaviriaj on 07/08/2016.
 */
@NoRepositoryBean
public class AbstractRepositoryImpl<E, K extends Serializable> extends SimpleJpaRepository<E, K> implements AbstractRepository<E, K> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRepositoryImpl.class);

    public static final String IGUAL            = "=";
    public static final String DIFERENTE        = "<>";
    public static final String MAYOR_QUE        = ">";
    public static final String MENOR_QUE        = "<";
    public static final String MENOR_IGUAL_QUE  = "<=";
    public static final String MAYOR_IGUAL_QUE  = ">=";
    public static final String LIKE             = " LIKE ";
    public static final String IS_NULL          = " IS NULL";
    public static final String PUNTO = ".";

    private final EntityManager entityManager;
    private final JpaEntityInformation<?, ?> entityInformation;

    @Autowired
    DateManager dateManagerBean;

    public AbstractRepositoryImpl(JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;

        if (dateManagerBean == null){
            dateManagerBean = new DateManager();
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<E> findAll(String[] filter, Integer limit, Integer offset, String[] sort) {
        String where = "";
        if (filter != null) {
            where = procesarWhere(filter);
            LOGGER.debug("GET {}", where);
        }

        String order = procesarOrder(sort);
        LOGGER.debug("GET ORDER {}", order);

        String queryCompleto = String.format("SELECT x FROM %s x %s %s", entityInformation.getEntityName(), where, order);
        LOGGER.debug("GET Query {}", queryCompleto);

        Query query = entityManager.createQuery(queryCompleto);

        if (filter != null) {
            query = procesarFilter(filter, query);
        }

        if (limit != null) {
            query.setMaxResults(limit);
        }

        if (offset != null) {
            query.setFirstResult(offset);
        }

        return query.getResultList();
    }

    @Override
    public Long count(String[] filter) {
        String where = "";
        if (filter != null) {
            where = procesarWhere(filter);
        }

        Query query = entityManager.createQuery(String.format("SELECT COUNT(x) FROM %s x %s", entityInformation.getEntityName(), where));

        if (filter != null) {
            query = procesarFilter(filter, query);
        }

        return (Long) query.getSingleResult();
    }

    private Query procesarFilter(String[] filter, Query query) {
        int filterCount = 1;
        Query queryRespuesta = query;
        for (String item : filter) {
            if (contieneOperador(item)) {
                queryRespuesta = procesarFilter(query, item, filterCount);
                filterCount++;
            }
        }
        return queryRespuesta;
    }

    private Query procesarFilter(Query query, String item, Integer filterCount) {
        Query nuevoQuery = query;

        String nombreParametro = String.format("parametro%s", filterCount);
        String valorParametro   = extraerValorParametro(item);

        if ("Integer".equals(nuevoQuery.getParameter(nombreParametro).getParameterType().getSimpleName())){
            nuevoQuery.setParameter(nombreParametro, Integer.parseInt(valorParametro));
        } else if ("Long".equals(nuevoQuery.getParameter(nombreParametro).getParameterType().getSimpleName())) {
            nuevoQuery.setParameter(nombreParametro, Long.parseLong(valorParametro));
        } else if ("String".equals(nuevoQuery.getParameter(nombreParametro).getParameterType().getSimpleName())) {
            if (item.toUpperCase().contains(LIKE)) {
                nuevoQuery.setParameter(nombreParametro, "%" + valorParametro.toUpperCase() + "%");
            } else{
                nuevoQuery.setParameter(nombreParametro, valorParametro.toUpperCase());
            }
        } else if ("Boolean".equals(nuevoQuery.getParameter(nombreParametro).getParameterType().getSimpleName())){
            if ("true".equalsIgnoreCase(valorParametro)) {
                nuevoQuery.setParameter(nombreParametro, true);
            }else if ("false".equalsIgnoreCase(valorParametro)) {
                nuevoQuery.setParameter(nombreParametro, false);
            }
        } else if ("Date".equals(nuevoQuery.getParameter(nombreParametro).getParameterType().getSimpleName())){
            if (valorParametro.length() == 10) {
                nuevoQuery.setParameter(nombreParametro, dateManagerBean.stringToDate(valorParametro, Constantes.FORMATO_FECHA_WEB));
            } else {
                nuevoQuery.setParameter(nombreParametro, dateManagerBean.stringToDate(valorParametro, Constantes.FORMATO_FECHA_HORA_WEB));
            }
        }

        return nuevoQuery;
    }

    private String procesarOrder(String[] sort) {
        String respuesta = "";
        int orderCount = 1;
        if (sort != null) {
            StringBuilder order = new StringBuilder();
            for (String item : sort) {
                if (orderCount == 1) {
                    order.append(" x." + item);
                } else {
                    order.append(", x." + item);
                }
                orderCount++;
            }

            if (orderCount > 1) {
                order.insert(0, "ORDER BY ");
            }
            respuesta = order.toString();
        }
        return respuesta;
    }

    private String procesarWhere(String[] filter) {
        StringBuilder where = new StringBuilder();
        int filterCount = 1;

        Class<E> entityClass = (Class<E>) entityInformation.getJavaType();

        for (String item : filter) {
            if (contieneOperador(item)) {
                String nombreCampo          = extraerNombreCampo(item);
                String nombreParametro      = String.format("parametro%s", filterCount);
                String operadorParametro    = extraerOperadorParametro(item);

                String auxWhere;

                if(item.contains(IS_NULL)){
                    auxWhere = " x." + item;
                } else{
                    Type type = getType(entityClass, nombreCampo);
                    if (type != null &&  type.equals(String.class)) {
                        auxWhere = " upper(x." + nombreCampo + ") " + operadorParametro + ":" + nombreParametro;
                    } else {
                        auxWhere = " x." + nombreCampo + " " + operadorParametro + ":" + nombreParametro;
                    }
                }

                if (filterCount == 1) {
                    where.append(auxWhere);
                } else {
                    where.append(" AND " + auxWhere);
                }

                filterCount++;
            }
        }

        if (filterCount > 1) {
            where.insert(0, "WHERE ");
        }

        return where.toString();
    }

    private Type getType(Class<E> entityClass, String nombreCampo) {
        Field field;
        Type type = null;

        try {
            if (!nombreCampo.contains(PUNTO)) {
                field = entityClass.getDeclaredField(nombreCampo);
                type = field.getGenericType();
            } else {
                String claseCampo       = nombreCampo.substring(0, nombreCampo.indexOf(PUNTO));
                String nuevoNombreCampo = nombreCampo.substring(nombreCampo.indexOf(PUNTO) + Constantes.UNO);
                type = entityClass.getDeclaredField(claseCampo).getType().getDeclaredField(nuevoNombreCampo).getGenericType();
            }
        } catch (NoSuchFieldException e) {
            LOGGER.error("Error procesando Where", e);
        }
        return type;
    }

    private boolean contieneOperador(String item) {
        boolean respuesta = false;
        if (item != null){
            respuesta = item.contains(IGUAL);
            respuesta = respuesta || item.contains(MAYOR_QUE);
            respuesta = respuesta || item.contains(MENOR_QUE);
            respuesta = respuesta || item.contains(DIFERENTE);
            respuesta = respuesta || item.contains(MENOR_IGUAL_QUE);
            respuesta = respuesta || item.contains(MAYOR_IGUAL_QUE);
            respuesta = respuesta || item.contains(LIKE);
        }
        return respuesta;
    }

    private String extraerNombreCampo(String item) {
        String x = procesarOperadores(item);
        if (x != null) {
            return x;
        }

        if (item.contains(IGUAL)) {
            return item.substring(0, item.indexOf('='));
        }
        return "";
    }

    private String extraerValorParametro(String item) {
        if (item.contains(DIFERENTE)) {
            return item.substring(item.indexOf(DIFERENTE) + Constantes.DOS);
        }

        if (item.contains(MENOR_IGUAL_QUE)) {
            return item.substring(item.indexOf(MENOR_IGUAL_QUE) + Constantes.DOS);
        }

        if (item.contains(MAYOR_IGUAL_QUE)) {
            return item.substring(item.indexOf(MAYOR_IGUAL_QUE) + Constantes.DOS);
        }

        if (item.contains(MAYOR_QUE)) {
            return item.substring(item.indexOf(MAYOR_QUE) + Constantes.UNO);
        }

        if (item.contains(MENOR_QUE)) {
            return item.substring(item.indexOf(MENOR_QUE) + Constantes.UNO);
        }

        if (item.contains(IGUAL)) {
            return item.substring(item.indexOf(IGUAL) + Constantes.UNO);
        }

        if (item.toUpperCase().contains(LIKE)) {
            return item.substring(item.indexOf(LIKE) + Constantes.SEIS);
        }

        return "";
    }

    private String extraerOperadorParametro(String item) {
        if (item.contains(DIFERENTE)) {
            return DIFERENTE;
        }

        if (item.contains(MENOR_IGUAL_QUE)) {
            return MENOR_IGUAL_QUE;
        }

        if (item.contains(MAYOR_IGUAL_QUE)) {
            return MAYOR_IGUAL_QUE;
        }

        if (item.contains(MAYOR_QUE)) {
            return MAYOR_QUE;
        }

        if (item.contains(MENOR_QUE)) {
            return MENOR_QUE;
        }

        if (item.contains(IGUAL)) {
            return IGUAL;
        }

        if (item.toUpperCase().contains(LIKE)) {
            return LIKE;
        }

        return "";
    }

    private String procesarOperadores(String item) {
        if (item.contains(DIFERENTE)) {
            return item.substring(0, item.indexOf(DIFERENTE));
        }

        if (item.contains(MENOR_IGUAL_QUE)) {
            return item.substring(0, item.indexOf(MENOR_IGUAL_QUE));
        }

        if (item.contains(MAYOR_IGUAL_QUE)) {
            return item.substring(0, item.indexOf(MAYOR_IGUAL_QUE));
        }

        if (item.contains(MAYOR_QUE)) {
            return item.substring(0, item.indexOf(MAYOR_QUE));
        }

        if (item.contains(MENOR_QUE)) {
            return item.substring(0, item.indexOf(MENOR_QUE));
        }

        if (item.contains(LIKE)) {
            return item.substring(0, item.indexOf(LIKE));
        }
        return null;
    }
}
