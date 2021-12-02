package com.gamedev;

import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.HashMap;

public class CommandParserClass {

    public interface MethodRunner {
        void run(String[] s);
    }

    public static final HashMap<String, MethodRunner> CommandList = new HashMap<String, MethodRunner> ();

    public static void parseCommand(Update update){
        CommandExecutorClass.updateChatID(update);
        String[] input = update.getMessage().getText().split(" ");
        String data = "";  String command = input[0]; String argument = "";
        try {
             data = input[1];
             argument = input[2];
        } catch (Exception ignored) {}

        if (CommandList.containsKey(command)){
            try {
                String[] args = new String[] {data, argument};
                CommandList.get(command).run(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void initializeCommands(){
        CommandList.put("/start", args -> CommandExecutorClass.start());
        CommandList.put("/help", args -> CommandExecutorClass.help());
        CommandList.put("/pie", args -> CommandExecutorClass.pie());
        CommandList.put("/balance", args -> CommandExecutorClass.balance());

        CommandList.put("/add", CommandExecutorClass::add);
        CommandList.put("/price", CommandExecutorClass::price);
    }
}
