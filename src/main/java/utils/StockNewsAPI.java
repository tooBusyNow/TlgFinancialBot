package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private static final NewsAPIClient CLIENT = NewsAPI.newClientBuilder()
                                                .setApiKey(EnvVarReader.ReadEnvVar("NEWS_TOKEN"))
                                                .build();
    private static final Logger LOGGER = LoggerFactory.getLogger(StockNewsAPI.class);
    private static List<String> DESCRIPTIONS;

    private static JsonObject getTopNews(String query) throws IOException, InterruptedException {
        Map<String, String> request = EverythingParams.newBuilder()
                                                .setSearchQueryInTitle(query)
                                                .setSortBy("publishedAt")
                                                .setLanguage("en")
                                                .build();

        NewsAPIResponse response = CLIENT.getEverything(request);
        System.out.println(response.getStatusCode());
        System.out.println(query);

        return response.getBodyAsJson();
    }

    private static String parseArticles(JsonObject obj) {

        DESCRIPTIONS = new ArrayList<>();
        StringBuilder msg = new StringBuilder();
        int counter = 1;
        final int maxArticles = 5;

        JsonArray articles = obj.getAsJsonArray("articles");
        for (JsonElement article: articles){

            JsonObject jsonArt = (JsonObject) article;
            JsonObject source = jsonArt.getAsJsonObject("source");

            JsonElement name = source.get("name");
            JsonElement title = jsonArt.get("title");
            JsonElement url = jsonArt.get("url");
            JsonElement description = jsonArt.get("description");

            DESCRIPTIONS.add(description.toString());

            System.out.println(description);

            msg.append(counter).append(") ");
            for (JsonElement element : Arrays.asList(name, title, url)) {
                String strElem = element.toString();
                strElem = strElem.substring(1, strElem.length()-1);
                msg.append(strElem).append("\n");
            }
            msg.append("===========================\n");
            if (counter == maxArticles)
                break;
            counter++;
        } 
        return msg.toString();
    }

    public static String getNewsMessage(String query) {
        try {
            return parseArticles(getTopNews(query));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return query;
    }

    public static List<String> getDescriptions(){
        return DESCRIPTIONS;
    }
} 