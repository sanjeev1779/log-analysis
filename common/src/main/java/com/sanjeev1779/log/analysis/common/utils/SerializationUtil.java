package com.sanjeev1779.log.analysis.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

public class SerializationUtil {
    public final static ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(FAIL_ON_EMPTY_BEANS, false);

    public static String writeString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
