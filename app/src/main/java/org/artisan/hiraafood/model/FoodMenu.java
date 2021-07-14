package org.artisan.hiraafood.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FoodMenu {
    private final Map<String, Item> items = new HashMap<>();
    private final Map<String, FoodCategory> categories = new LinkedHashMap<>();

    public Item getItemBySku(String sku) {
        return items.get(sku);
    }

    public FoodCategory getCategory(String name) {
        return categories.get(name);
    }

    public List<FoodCategory> getCategories() {
        Collection<FoodCategory> categories = this.categories.values();
        List<FoodCategory> result = new ArrayList<>();
        for (FoodCategory c : categories) {
            result.add(c);
        }
        return result;
    }

    public List<String> getCategoryLabels() {
        Set<String> labels = categories.keySet();
        List<String> result = new ArrayList<>();
        for (String label : labels) {
            result.add(label);
        }
        return result;
    }

    public void addItem(Item item) {
        items.put(item.sku, item);
        for (String cat : item.categories) {
            FoodCategory category = categories.get(cat);
            if (category == null) {
                category = new FoodCategory(cat);
                categories.put(category.name, category);
            }
            category.addItem(item);

        }

    }

    public FoodCategory getCategoryAt(int position) {
        Iterator<FoodCategory> it = categories.values().iterator();
        for (int i = 0; i < position && it.hasNext(); i++) {
            it.next();
        }
        return it.next();
    }

    public int getCategoryCount() {
        return categories.size();
    }
}
