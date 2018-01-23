package com.altamontana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaviriaj on 07/08/2016.
 */
@NoRepositoryBean
public interface AbstractRepository<E, K extends Serializable> extends JpaRepository<E, K> {

    List<E> findAll(String[] filter, Integer limit, Integer offset, String[] sort);
    Long count(String[] filter);
}
