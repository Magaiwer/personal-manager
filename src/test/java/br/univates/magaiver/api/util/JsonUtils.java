package br.univates.magaiver.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

/**
 * @author Magaiver Santos
 */
@Component
public class JsonUtils {

    @SneakyThrows
    public static String serialize(Object object) {
        return new ObjectMapper().writeValueAsString(object);
    }
}
