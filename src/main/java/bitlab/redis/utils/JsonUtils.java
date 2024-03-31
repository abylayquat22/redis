package bitlab.redis.utils;

import bitlab.redis.entity.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class JsonUtils {

  private final ObjectMapper objectMapper;

  public String toJson(Object value) throws JsonProcessingException {
    return objectMapper.writeValueAsString(value);
  }

  public Long extractIdFromJson(String json) throws JsonProcessingException {
    return objectMapper.readTree(json).get("id").asLong();
  }

  public Long fromObject(Object object) throws JsonProcessingException {
    return extractIdFromJson(toJson(object));
  }
}
