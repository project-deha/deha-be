package me.utku.dehabe.generic;

public interface Action<R, P> {
    R execute(P p);
}
