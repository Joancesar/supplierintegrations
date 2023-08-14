package com.hotelbeds.supplierintegrations.hackertest.processor;

public interface Processor<I, O> {

    O process(I input);

}
