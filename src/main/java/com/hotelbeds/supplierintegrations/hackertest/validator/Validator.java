package com.hotelbeds.supplierintegrations.hackertest.validator;


public interface Validator<I, O> {
    O validate(I input);
}
