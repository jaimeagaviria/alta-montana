package com.altamontana.service.impl;

import com.altamontana.repository.AbstractRepository;
import com.altamontana.service.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by gaviriaj on 06/08/2016.
 */
@Service
@Transactional
public abstract class AbstractServiceImpl<E, K extends Serializable> implements AbstractService<E, K> {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    protected final boolean debug = LOGGER.isDebugEnabled();
    protected final boolean info = LOGGER.isInfoEnabled();

    protected AbstractRepository<E, K> daoObject;

    public AbstractServiceImpl(AbstractRepository<E, K> genericDao) {
        this.daoObject = genericDao;
    }

    public AbstractServiceImpl() {
    }

    @Override
    @Transactional(readOnly = false)
    public E save(E entity) {
        return daoObject.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public E patch(K id, E entity) {
        E newEntity = findOne(id);
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (value != null) {
                    field.set(newEntity, value);
                }
            } catch (IllegalAccessException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return newEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> findAll() {
        return daoObject.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> findAll(String[] filter, Integer limit, Integer offset, String[] sort) {
        return daoObject.findAll(filter, limit, offset, sort);
    }

    @Override
    @Transactional(readOnly = true)
    public E findOne(K id) {
        return daoObject.findOne(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteById(K id) {
        E entity = findOne(id);
        return delete(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(E entity) {
        boolean respuesta = false;
        try {
            daoObject.delete(entity);
            respuesta = true;
        } catch (Exception e) {
            respuesta = false;
            LOGGER.error(e.getMessage(), e);
        }
        return respuesta;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(K id) {
        return daoObject.exists(id);
    }

    @Override
    public Long count(String[] filter) {
        return daoObject.count(filter);
    }

}
