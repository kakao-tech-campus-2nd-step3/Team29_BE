package notai.common.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import notai.common.exception.type.JsonConversionException;

import java.io.IOException;

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
            throw new JsonConversionException("객체를 JSON 문자열로 변환하는 중 오류가 발생했습니다.");
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, typeReference);
        } catch (IOException e) {
            throw new JsonConversionException("JSON 문자열을 객체로 변환하는 중 오류가 발생했습니다.");
        }
    }
}
