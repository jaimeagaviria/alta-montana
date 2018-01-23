package com.altamontana.service;

import java.util.List;

/**
 * Created by gaviriaj on 06/08/2016.
 */
public interface AbstractService<E, K> {
    E save(E entity);

    E patch(K id, E entity);

    List<E> findAll();

    List<E> findAll(String[] filter, Integer limit, Integer offset, String[] sort);

    E findOne(K id);

    boolean deleteById(K id);

    boolean delete(E entity);

    boolean exists(K id);

    Long count(String[] filter);
}
