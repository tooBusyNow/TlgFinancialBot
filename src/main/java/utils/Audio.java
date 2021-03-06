package utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.objects.InputFile;


public class Audio {
    private static final SpeechAPI speechApiClient = new SpeechAPI();
    private static final Logger logger = LoggerFactory.getLogger(Audio.class);
    public static SendVoice returnAudio(String text) {

        SendVoice audio = new SendVoice();
        ByteString bytes = null;
        try {
            bytes = speechApiClient.textToSpeech(text);
        } catch (IOException e) {
            logger.error("Error happened during TTS conversion", e);
        } if (bytes != null)
            audio.setVoice(new InputFile(new ByteArrayInputStream(bytes.toByteArray()), "audio.mp3"));
        return audio;
    }    
}
