package com.github.happiexplorer.persistence.query;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;

public final class DeleteQuery<R> extends BaseQuery<R, DeleteQuery<R>> {

    private final EntityManager manager;

    private final CriteriaBuilder builder;

    private final CriteriaDelete<R> query;

    private final Root<R> root;

    DeleteQuery(final EntityManager manager, final Class<R> targetEntity) {
        this.manager = manager;
        this.builder = manager.getCriteriaBuilder();
        this.query = this.builder.createCriteriaDelete(targetEntity);
        this.root = this.query.from(targetEntity);
    }

    @Override
    protected DeleteQuery<R> self() {
        return this;
    }

    public int execute() {
        if (!this.predicates.isEmpty()) {
            var computedPredicated = buildPredicates(this.builder, this.root, this.query, this.predicates);
            this.query.where(computedPredicated);
        }
        return this.manager.createQuery(query).executeUpdate();
    }
}