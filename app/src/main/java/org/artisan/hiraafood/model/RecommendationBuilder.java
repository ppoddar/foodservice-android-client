package org.artisan.hiraafood.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;

public class RecommendationBuilder {
    public static Recommendations build(InputStream in) {
        Recommendations recos = new Recommendations();
        JsonArray array =
        JsonParser.parseReader(new InputStreamReader(in)).getAsJsonArray();
        for (JsonElement e : array) {
            Recommendation r = Recommendation.fromJson(e.getAsJsonObject());
            recos.addRecommendation(r);
        }
        return recos;
    }
}
