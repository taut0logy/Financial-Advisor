package com.advancedprogramminglab.financial_advisor;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class JSONHandler {

    public static String toJSON(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public static Object fromJSON(String json, Class c) {
        Gson gson = new Gson();
        return gson.fromJson(json, c);
    }

    public static Object getFromFile(String path,Class o) throws IOException {
        FileReader fileReader = new FileReader(path);
        Gson gson = new Gson();
        return gson.fromJson(fileReader, o);
    }

}
