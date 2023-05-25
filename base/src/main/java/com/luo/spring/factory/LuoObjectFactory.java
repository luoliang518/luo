package com.luo.spring.factory;

@FunctionalInterface
public interface LuoObjectFactory<T> {
    T getObject() throws RuntimeException;
}