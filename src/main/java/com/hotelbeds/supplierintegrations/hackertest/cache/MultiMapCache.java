package com.hotelbeds.supplierintegrations.hackertest.cache;


import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface MultiMapCache<K, V> {

    void put(K key, V value);

    void removeIf(Predicate<Map.Entry<K, List<V>>> filter);

    void removeAllIf(Predicate<V> filter);

    int size(K key);
}
