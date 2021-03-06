package com.transloadit.sdk;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OptionsBuilderTest {
    public OptionsBuilder optionsBuilder;

    @Before
    public void setUp() throws Exception {
        optionsBuilder = new OptionsBuilder();
        optionsBuilder.steps = new Steps();
        optionsBuilder.options = new HashMap<String, Object>();
    }


    @Test
    public void addStep() throws Exception {
        optionsBuilder.addStep("encode", "/video/encode", new HashMap<String, Object>());

        assertEquals(optionsBuilder.steps.getStep("encode").robot , "/video/encode");
    }

    @Test
    public void removeStep() throws Exception {
        optionsBuilder.addStep("encode", "/video/encode", new HashMap<String, Object>());
        assertTrue(optionsBuilder.steps.all.containsKey("encode"));

        optionsBuilder.removeStep("encode");
        assertFalse(optionsBuilder.steps.all.containsKey("encode"));
    }

    @Test
    public void addOptions() throws Exception {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("foo", "bar");
        options.put("red", "color");

        optionsBuilder.addOptions(options);
        assertEquals(options, optionsBuilder.options);
    }

    @Test
    public void addOption() throws Exception {
        optionsBuilder.addOption("foo", "bar");
        assertEquals(optionsBuilder.options.get("foo"), "bar");
    }

}