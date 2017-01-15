package com.wonggigi.util;

/**
 * Created by handle on 17-1-15.
 */
public class ThreeTuple<A,B,C> {
    public final A first;
    public final B second;
    public final C third;

    public ThreeTuple(A a,B b,C c) {
        this.first = a;
        this.second = b;
        this.third=c;
    }
}
