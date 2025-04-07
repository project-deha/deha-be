package org.deha.be.generic.query;

import org.springframework.data.jpa.domain.Specification;

public interface ParameterlessQuery<R, E> {
    /**
     * Query the database with the prepared specification only when no parameters are needed.
     *
     * @return Result of the query.
     */
    R query();

    /**
     * Prepare specification object to query.
     *
     * @return Specification object to query.
     */
    Specification<E> prepareSpecification();
}
