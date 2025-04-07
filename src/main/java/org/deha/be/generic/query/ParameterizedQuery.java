package org.deha.be.generic.query;

import org.springframework.data.jpa.domain.Specification;

public interface ParameterizedQuery<R, P, E> {
    /**
     * Query the database with the given parameter object.
     *
     * @param p parameter object to query.
     * @return Result of the query.
     */
    R query(P p);

    /**
     * Prepare specification object to query.
     *
     * @param p parameter object to prepare specification.
     * @return Specification object to query.
     */
    Specification<E> prepareSpecification(P p);
}
