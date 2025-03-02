package me.utku.dehabe.generic;

public interface Query<R, P> {
    R query(P p);
}
