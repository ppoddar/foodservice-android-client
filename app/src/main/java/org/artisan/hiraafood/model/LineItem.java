package org.artisan.hiraafood.model;

public class LineItem {
    public Item item;
    public int quantity;
    public String comment;

    public LineItem() {

    }
    public LineItem(Item i) {
        this(i, 1);
    }
    public LineItem(Item i, int q) {
        item = i;
        quantity = q;
    }
}
