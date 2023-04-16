package org.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static final String URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static void main(String[] args) throws IOException {
        var gson = new Gson();
        Cat[] cats;

        var allCats = extractDataCat();

        cats = gson.fromJson(allCats, Cat[].class);

        List<Cat> catsWithVotes = Arrays.stream(cats)
                .filter(cat -> cat.getUpvotes() > 0)
                .toList();

        var gsonPP = new GsonBuilder().setPrettyPrinting().create();

        String s = gsonPP.toJson(catsWithVotes);

        System.out.println(s);
    }

    private static String extractDataCat() throws IOException {

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        //HttpGet request = new HttpGet(URL);

        CloseableHttpResponse response = httpClient.execute(new HttpGet(URL));

        return (new String(response.getEntity().getContent().readAllBytes()));
    }
}