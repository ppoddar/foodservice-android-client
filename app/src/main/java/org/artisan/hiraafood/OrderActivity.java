package org.artisan.hiraafood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.artisan.hiraafood.model.Cart;
import org.artisan.hiraafood.model.FoodMenu;
import org.artisan.hiraafood.model.Item;
import org.artisan.hiraafood.model.LineItem;
import org.artisan.hiraafood.model.Recommendation;
import org.artisan.hiraafood.server.FoodServiceDataModel;
import org.artisan.hiraafood.view.ItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Presents a form to order a food item.
 */
public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        final FoodApplication app = (FoodApplication) getApplication();
        FoodServiceDataModel model = app.getDataModel();
        model.getCart().observe(this, new CartObserver(this, this.<ViewGroup>findViewById(R.id.toolbar_main)));
    }

    @Override
    public void onResume() {
        super.onResume();

        String sku = getIntent().getExtras().getString(Constants.KEY_SKU);
        final FoodApplication app = (FoodApplication)this.getApplication();
        final FoodMenu menu = app.getDataModel().getMenu().getValue();
        final Item item     = menu.getItemBySku(sku);
        ItemView.create(ItemView.Type.OrderForm, this).create(R.id.item, item);

        ImageButton incrementQuantity = findViewById(R.id.action_increment_quantity);
        ImageButton decrementQuantity = findViewById(R.id.action_increment_quantity);
        Button orderButton = findViewById(R.id.action_order);

        incrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOrderQuantity(1);
            }
        });
        decrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOrderQuantity(-1);
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineItem li = new LineItem();
                li.item = item;
                li.quantity = getOrderQuantity();
                Cart cart = app.getDataModel().getCart().getValue();
                cart.addLineItem(li);
                // adding will update the toolbar cart view
            }
        });

        final Recommendation reco = app.getDataModel()
                .getRecommendations().getValue()
                .getRecommendations(item.sku);
        if (reco != null && !reco.isEmpty()) {
            List<Item> recommendedItems = new ArrayList<>();
            for (String o : reco.others) {
                recommendedItems.add(menu.getItemBySku(o));
            }
            ItemAdapter adapter = new ItemAdapter(this,
                    ItemView.Type.RecommendationList,
                    recommendedItems);
            ListView item_recommendations = findViewById(R.id.order_recommendations);
            item_recommendations.setAdapter(adapter);
        }

    }

    void changeOrderQuantity(int delta) {
        int q = getOrderQuantity();
        if (q+delta > 0) {
            q += delta;
            setOrderQuantity(q);
        }
        ImageButton incrementQuantity = findViewById(R.id.action_increment_quantity);
        ImageButton decrementQuantity = findViewById(R.id.action_increment_quantity);
        incrementQuantity.setClickable(q > 0);
        decrementQuantity.setClickable(q > 1);
    }

    int getOrderQuantity() {
        EditText quantity = findViewById(R.id.order_quantity);
        return Integer.parseInt(quantity.toString());
    }

    void setOrderQuantity(int q) {
        EditText quantity = findViewById(R.id.order_quantity);
        quantity.setText(Integer.toString(q));
    }
}