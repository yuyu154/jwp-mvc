package nextstep.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.exception.ObjectMapperException;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {
    private static final int BORDER_OF_SIZE = 1;
    private static final String EMPTY_STRING = "";

    public static <T> T toObject(String json, Class<T> clazz) throws ObjectMapperException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            configObjectMapper(objectMapper);
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ObjectMapperException(e);
        }
    }

    private static void configObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
    }

    public static String toJsonString(Map<String, ?> model) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (model.size() == BORDER_OF_SIZE) {
                Object value = model.values().toArray()[0];
                return objectMapper.writeValueAsString(value);
            }

            if (model.size() > BORDER_OF_SIZE) {
                return objectMapper.writeValueAsString(model);
            }

            return EMPTY_STRING;
        } catch (JsonProcessingException e) {
            throw new ObjectMapperException(e);
        }
    }
}
