package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static final String URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static void main(String[] args) throws IOException {

        Gson gson = new Gson();
        var allCats = extractDataCat();
        var Cats = extractDataCat();

        Cat[] cats;
        cats = gson.fromJson(Cats, Cat[].class);
        List<Cat> factsPut = Arrays.stream(cats)
                .filter(cat -> cat.getUpvotes() > 0)
                .toList();

        var gsonPP = new GsonBuilder().setPrettyPrinting().create();
        String facts = gsonPP.toJson(factsPut);
        System.out.println(facts);
    }

    public static String extractDataCat() throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        CloseableHttpResponse response = httpClient.execute(new HttpGet(URL));

        return (new String(response.getEntity().getContent().readAllBytes()));
    }

}