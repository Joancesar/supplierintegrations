package com.hotelbeds.supplierintegrations.hackertest.cache.impl;


import com.hotelbeds.supplierintegrations.hackertest.cache.MultiMapCache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class MultiValueMapCache<K, V> implements MultiMapCache<K, V> {

    private final Map<K, List<V>> store = new ConcurrentHashMap<>();

    @Override
    public void put(K key, V value) {
        store.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    @Override
    public void removeIf(Predicate<Map.Entry<K, List<V>>> filter) {
        this.store.entrySet().removeIf(filter);
    }

    @Override
    public void removeAllIf(Predicate<V> filter) {
        this.store.forEach((key, valueList) -> valueList.removeIf(filter));
    }

    @Override
    public int size(K key) {
        return this.store.getOrDefault(key, Collections.emptyList()).size();
    }

}
