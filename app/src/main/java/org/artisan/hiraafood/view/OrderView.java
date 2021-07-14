package org.artisan.hiraafood.view;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.artisan.hiraafood.model.Order;
import org.artisan.hiraafood.model.OrderItem;

public class OrderView extends ListView {
    private Order order;
    private int selection;
    public  OrderView(Context ctx, Order order) {
        super(ctx);
        this.order = order;
        addHeaderView(createHeaderView());
        addFooterView(createFooterView());
    }
    @Override
    public Order getAdapter() {
        return order;
    }
    @Override
    public View getSelectedView() {
        OrderItem item = order.getItem(selection);
        return new OrderItemView(this.getContext(), item);
    }

    @Override
    public void setSelection(int position) {
        this.selection = position;
    }

    View createHeaderView() {
        TextView view = new TextView(this.getContext());
        return view;
    }

    View createFooterView() {
        TextView view = new TextView(this.getContext());
        return view;
    }
}
