package org.artisan.hiraafood.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.artisan.hiraafood.model.FoodMenu;
import org.artisan.hiraafood.model.Item;

import java.io.InputStream;
import java.io.InputStreamReader;

public class FoodMenuBuilder {
    public static FoodMenu build(InputStream in) {
        FoodMenu menu = new FoodMenu();
        JsonArray array = JsonParser.parseReader(new InputStreamReader(in))
                .getAsJsonArray();
        for (JsonElement obj : array) {
            Item item = Item.fromJson(obj.getAsJsonObject());
            menu.addItem(item);
        }
        return menu;
    }
}
