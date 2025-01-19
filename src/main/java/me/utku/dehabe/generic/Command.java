package me.utku.dehabe.generic;

public interface Command<R, P> {
    R execute(P p);
}
