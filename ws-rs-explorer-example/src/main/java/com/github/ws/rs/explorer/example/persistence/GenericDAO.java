package com.github.ws.rs.explorer.example.persistence;


import java.util.Optional;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

@Singleton
public class GenericDAO {

    @Inject
    private EntityManager em;

    public GenericDAO() {
    }

    public <E> Optional<E> find(Class<E> entityClass, Object id) {
        var entity = this.em.find(entityClass, id);
        return Optional.ofNullable(entity);
    }
}
