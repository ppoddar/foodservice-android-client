package org.artisan.hiraafood.model;

import java.util.ArrayList;
import java.util.List;

public class FoodCategory {
    public String name;
    private List<Item> items = new ArrayList<>();

    public FoodCategory(String name) {
        this.name = name;
    }
    public String getName() { return name;}
    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
    public Item getItemAt(int idx) {
        return items.get(idx);
    }
    public int size() {
        return items.size();
    }


}
