package com.gamedev;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    private static final Bot bot = new Bot();
    public static Bot getBot() { return bot; }

    public static void main(String[] args) {
        try {
            JedisHandler.auth();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
