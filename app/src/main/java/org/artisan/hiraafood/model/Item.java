package org.artisan.hiraafood.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Item {
    public String sku;
    public String name;
    public String description;
    public String[] categories;
    public double price;
    public String image;

    public static Item fromJson(JsonObject json) {
        Item item = new Item();
        item.sku = json.get("sku").getAsString();
        item.name = json.get("name").getAsString();
        item.description = json.has("description") ? json.get("name").getAsString() : "";
        item.price = json.get("price").getAsDouble();
        JsonArray categories = json.get("categories").getAsJsonArray();
        item.categories = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            item.categories[i] = categories.get(i).getAsString();
        }
        item.image = json.get("image").getAsString();

        return item;
    }
}
