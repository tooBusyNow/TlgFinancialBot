package com.gamedev;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jzhangdeveloper.newsapi.net.NewsAPI;
import com.jzhangdeveloper.newsapi.net.NewsAPIClient;
import com.jzhangdeveloper.newsapi.net.NewsAPIResponse;
import com.jzhangdeveloper.newsapi.params.EverythingParams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockNewsAPI {
    static NewsAPIClient client = NewsAPI.newClientBuilder()
                                            .setApiKey(EnvVarReader.ReadEnvVar("NEWS_TOKEN"))
                                            .build();

    private static final Logger logger = LoggerFactory.getLogger(StockNewsAPI.class);

    private static JsonObject getTopNews(String query) throws IOException, InterruptedException {
        Map<String, String> everythingParams = EverythingParams.newBuilder()
                                                .setSearchQuery(query)
                                                .setSortBy("publishedAt")
                                                .setLanguage("en")
                                                .build();

        NewsAPIResponse response = client.getEverything(everythingParams);
        logger.error(String.valueOf(response.getStatusCode()));

        JsonObject headlinesJson = response.getBodyAsJson();
        return headlinesJson;
    }

    private static String parseArticles(JsonObject obj) {
        String msg = "";
        Integer counter = 0;

        JsonArray articles = obj.getAsJsonArray("articles");
        for (JsonElement art: articles){
            JsonObject jsonArt = (JsonObject) art;
            JsonObject source = jsonArt.getAsJsonObject("source");
            JsonElement name = source.get("name");
            JsonElement title = jsonArt.get("title");
            JsonElement url = jsonArt.get("url");
            for (JsonElement element : Arrays.asList(name, title, url)) {
                msg += element.toString() + "\n"; 
            }
            msg += "===========================\n";
            if (counter == 5)
                break;
            counter++;
        } 
        return msg;
    }

    public static String getNewsMessage(String query) {
        try {
            return parseArticles(getTopNews(query));
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return query;
    }
} 