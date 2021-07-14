package org.artisan.hiraafood.model;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import org.artisan.hiraafood.R;

import java.util.List;

public class Order extends ArrayAdapter<OrderItem> {
    public Order(@NonNull Context context, OrderItem[] data) {
        super(context, R.layout.order_item, data);
    }
}
