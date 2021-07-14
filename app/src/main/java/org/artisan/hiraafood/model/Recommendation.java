package org.artisan.hiraafood.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Recommendation {
    public String sku;
    public List<String> others = new ArrayList<>();

    public static Recommendation fromJson(JsonObject json) {
        Recommendation r = new Recommendation();
        r.sku = json.get("sku").getAsString();
        JsonArray array = json.get("recos").getAsJsonArray();
        for (JsonElement e : array) {
            r.others.add( e.getAsString());
        }
        return r;
    }

    public  boolean isEmpty() {
        return others.isEmpty();
    }

    public void merge(Recommendation other) {
        if (this.sku.equals(other.sku)) {
            this.others.addAll(other.others);
        }
    }
}
