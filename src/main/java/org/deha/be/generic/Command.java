package org.deha.be.generic;

public interface Command<R, P> {
    R execute(P p);
}
