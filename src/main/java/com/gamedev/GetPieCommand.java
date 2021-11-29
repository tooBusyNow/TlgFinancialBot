package com.gamedev;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import java.io.File;

public class GetPieCommand {
    public static SendPhoto pie(SendPhoto sendPhoto, String chat_id){
        DiagramClass.CreateDiagram();
        sendPhoto.setChatId(chat_id);
        sendPhoto.setCaption("This diagram was made for test. Enjoy!");
        sendPhoto.setPhoto(new InputFile(new File("TD.jpeg")));
        return sendPhoto;
    }
}