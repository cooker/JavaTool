package com.cooker.zoom.helper.utils.convert;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaseEntityConvert<A, B> implements IEntityPackage<A, B> {
    private final Function<A, B> fromA;
    private final Function<B, A> fromB;

    public BaseEntityConvert(Function<A, B> fromA, Function<B, A> fromB) {
        this.fromA = fromA;
        this.fromB = fromB;
    }


    public final B convertFromA(final A entity) {
        return fromA.apply(entity);
    }

    public final A convertFromB(final B entity) {
        return fromB.apply(entity);
    }


    public final List<B> convertFromListA(final Collection<A> entitys) {
        return entitys.stream().map(this::convertFromA).collect(Collectors.toList());
    }

    public final List<A> convertFromListB(final Collection<B> entitys) {
        return entitys.stream().map(this::convertFromB).collect(Collectors.toList());
    }

    @Override
    public A fill(B[] entitys) {
        return null;
    }
}
