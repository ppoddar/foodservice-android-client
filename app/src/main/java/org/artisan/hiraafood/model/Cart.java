package org.artisan.hiraafood.model;


import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<String,LineItem> items = new LinkedHashMap<>();

    public int getItemCount() {
        int count = 0;
        for (LineItem li : items.values()) {
            count += li.quantity;
        }
        return count;
    }

    public void addLineItem(LineItem li) {
        String sku = li.item.sku;
        LineItem existing = items.get(sku);
        if (existing == null) {
            items.put(sku, li);
        } else {
            existing.quantity += li.quantity;
            existing.comment  += li.comment;
        }
    }

    public double estimatePrice() {
        double total = 0;
        for (LineItem li : items.values()) {
            total += li.item.price;
        }
        return total;
    }


}
