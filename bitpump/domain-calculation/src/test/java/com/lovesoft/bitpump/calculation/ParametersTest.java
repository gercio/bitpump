package com.lovesoft.bitpump.calculation;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created 03.03.2018 17:16.
 */
public class ParametersTest {

    @Test
    public void testGetSet() {
        Parameters p = new Parameters();
        p.put("PARAM_NAME", "param value");
        String value = p.get("PARAM_NAME");
        Assertions.assertEquals("param value", value);
    }

    @Test
    public void testGetLong() {
        Parameters p = new Parameters();
        p.put("PARAM_NAME", "123");
        Optional<Long> oLong = p.getOptionalLong("PARAM_NAME");
        Assertions.assertEquals((Long) 123l, oLong.get());
    }


    @Test
    public void testGetLongWhenEmpty() {
        Parameters p = new Parameters();
        Optional<Long> oLong = p.getOptionalLong("PARAM_NAME");
        Assertions.assertFalse(oLong.isPresent());
    }

    @Test
    public void testGetLongWheShitty() {
        Parameters p = new Parameters();
        p.put("PARAM_NAME", "this is not proper long for sure");
        Optional<Long> oLong = p.getOptionalLong("PARAM_NAME");
        Assertions.assertFalse(oLong.isPresent());
    }
    @Test
    public void testGetDouble() {
        Parameters p = new Parameters();
        p.put("PARAM_NAME", "123");
        Optional<Double> oLong = p.getOptionalDouble("PARAM_NAME");
        Assertions.assertEquals((Double) 123d, oLong.get());
    }


    @Test
    public void testGetDoubleWhenEmpty() {
        Parameters p = new Parameters();
        Optional<Long> oLong = p.getOptionalLong("PARAM_NAME");
        Assertions.assertFalse(oLong.isPresent());
    }

    @Test
    public void testGetDoubleWheShitty() {
        Parameters p = new Parameters();
        p.put("PARAM_NAME", "this is not proper double for sure");
        Optional<Long> oLong = p.getOptionalLong("PARAM_NAME");
        Assertions.assertFalse(oLong.isPresent());
    }

}
