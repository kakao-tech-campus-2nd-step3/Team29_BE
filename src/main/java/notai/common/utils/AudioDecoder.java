package notai.common.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class AudioDecoder {

    public byte[] decode(String audioData) throws IllegalArgumentException {
        String base64AudioData = removeMetaData(audioData);
        return Base64.getDecoder().decode(base64AudioData);
    }

    private static String removeMetaData(String audioData) {
        return audioData.split(",")[1];
    }
}
