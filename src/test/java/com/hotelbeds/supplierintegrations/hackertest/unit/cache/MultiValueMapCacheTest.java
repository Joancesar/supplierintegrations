package com.hotelbeds.supplierintegrations.hackertest.unit.cache;

import com.hotelbeds.supplierintegrations.hackertest.cache.impl.MultiValueMapCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiValueMapCacheTest {

    MultiValueMapCache<String, Integer> mapCache;

    @BeforeEach
    void setUp() {
        mapCache = new MultiValueMapCache<>();
    }

    @Test
    void shouldPut_withSameKey_thenSizeIsUpdated() {
        String key = "first";
        int value1 = 42;
        int value2 = 34;

        mapCache.put(key, value1);
        mapCache.put(key, value2);
        mapCache.put(key, value2);
        int size = mapCache.size(key);

        assertEquals(3, size);
    }

    @Test
    void shouldPut_withDifferentKeys_thenSizeIsUpdated() {
        String firstKey = "first";
        int firstValue1 = 42;
        int firstValue2 = 42;
        String secondKey = "second";
        int secondValue1 = 24;
        int secondValue2 = 24;
        int secondValue3 = 24;


        mapCache.put(firstKey, firstValue1);
        mapCache.put(firstKey, firstValue2);
        mapCache.put(secondKey, secondValue1);
        mapCache.put(secondKey, secondValue2);
        mapCache.put(secondKey, secondValue3);
        int firstSize = mapCache.size(firstKey);
        int secondSize = mapCache.size(secondKey);

        assertEquals(2, firstSize);
        assertEquals(3, secondSize);
    }

    @Test
    void shouldRemoveIf_withSameKey_thenSizeIsUpdated() {
        String key = "first";
        int value1 = 42;
        int value2 = 34;

        mapCache.put(key, value1);
        mapCache.put(key, value2);
        mapCache.put(key, value2);
        mapCache.removeIf(entry -> "first".equals(entry.getKey()));
        int size = mapCache.size(key);

        assertEquals(0, size);
    }

    @Test
    void shouldRemoveIf_withDifferentKeys_thenSizeIsUpdated() {
        String firstKey = "first";
        int firstValue1 = 42;
        int firstValue2 = 15;
        String secondKey = "second";
        int secondValue1 = 24;
        int secondValue2 = 24;
        int secondValue3 = 10;

        mapCache.put(firstKey, firstValue1);
        mapCache.put(firstKey, firstValue2);
        mapCache.put(secondKey, secondValue1);
        mapCache.put(secondKey, secondValue2);
        mapCache.put(secondKey, secondValue3);
        mapCache.removeIf(entry -> "second".equals(entry.getKey()));
        int firstSize = mapCache.size(firstKey);
        int secondSize = mapCache.size(secondKey);

        assertEquals(2, firstSize);
        assertEquals(0, secondSize);
    }

    @Test
    void shouldRemoveAllIf_whenPutSameKeys_thenSizeIsUpdated() {
        String firstKey = "first";
        int firstValue1 = 42;
        int firstValue2 = 15;

        mapCache.put(firstKey, firstValue1);
        mapCache.put(firstKey, firstValue1);
        mapCache.put(firstKey, firstValue2);
        mapCache.removeAllIf(v -> v < 20);
        int firstSize = mapCache.size(firstKey);

        assertEquals(2, firstSize);
    }

    @Test
    void shouldRemoveAllIf_whenPutDifferentKeys_thenSizeIsUpdated() {
        String firstKey = "first";
        int firstValue1 = 42;
        int firstValue2 = 15;
        String secondKey = "second";
        int secondValue1 = 24;
        int secondValue2 = 24;
        int secondValue3 = 10;

        mapCache.put(firstKey, firstValue1);
        mapCache.put(firstKey, firstValue2);
        mapCache.put(secondKey, secondValue1);
        mapCache.put(secondKey, secondValue2);
        mapCache.put(secondKey, secondValue3);
        mapCache.removeAllIf(v -> v < 20);
        int firstSize = mapCache.size(firstKey);
        int secondSize = mapCache.size(secondKey);

        assertEquals(1, firstSize);
        assertEquals(2, secondSize);
    }

    @Test
    void sizeWithExistingKey() {
        String firstKey = "first";
        int firstValue1 = 42;

        mapCache.put(firstKey, firstValue1);
        mapCache.put(firstKey, firstValue1);
        mapCache.put(firstKey, firstValue1);
        int size = mapCache.size("first");

        assertEquals(3, size);
    }

    @Test
    void sizeWithNonExistingKey() {
        int size = mapCache.size("nonExistingKey");

        assertEquals(0, size);
    }
}
