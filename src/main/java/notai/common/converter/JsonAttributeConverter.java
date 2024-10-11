package notai.common.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import notai.common.exception.type.JsonConversionException;

import java.io.IOException;

import static notai.common.exception.ErrorMessages.JSON_CONVERSION_ERROR;

@Slf4j
@Converter
public class JsonAttributeConverter<T> implements AttributeConverter<T, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<T> typeReference;

    public JsonAttributeConverter(TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("객체를 JSON 문자열로 변환하는 중 오류가 발생했습니다.");
            throw new JsonConversionException(JSON_CONVERSION_ERROR);
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, typeReference);
        } catch (IOException e) {
            log.error("JSON 문자열을 객체로 변환하는 중 오류가 발생했습니다.");
            throw new JsonConversionException(JSON_CONVERSION_ERROR);
        }
    }
}
