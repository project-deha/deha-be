package org.deha.be.generic.command;

public interface Command<R, P> {
    R execute(P p);
}
