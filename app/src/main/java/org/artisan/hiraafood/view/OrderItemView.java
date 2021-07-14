package org.artisan.hiraafood.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import org.artisan.hiraafood.model.OrderItem;

public class OrderItemView extends View {
    private  OrderItem item;
    public OrderItemView(Context ctx, OrderItem item) {
        super(ctx);

    }

}
