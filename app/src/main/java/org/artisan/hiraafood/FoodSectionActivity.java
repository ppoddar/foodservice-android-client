package org.artisan.hiraafood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import org.artisan.hiraafood.model.Cart;
import org.artisan.hiraafood.model.FoodMenu;
import org.artisan.hiraafood.model.Item;
import org.artisan.hiraafood.server.FoodServiceDataModel;
import org.artisan.hiraafood.view.CustomToolbar;

public class FoodSectionActivity extends FragmentActivity {
    private static final String TAG = FoodSectionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_section);
        final FoodApplication app = (FoodApplication) getApplication();
        FoodServiceDataModel model = app.getDataModel();
        CustomToolbar toolbar = findViewById(R.id.toolbar_main);
        Observer<Cart> observer = toolbar.newCartObserver(this);
        model.getCart().observe(this, observer);
        model.getMenu().observe(this, new MenuObserver());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    class MenuObserver implements Observer<FoodMenu> {
        @Override
        public void onChanged(final FoodMenu foodMenu) {
            ExpandableListView categories = findViewById(R.id.list_food_categories);
            ExpandableListAdapter adapter = new FoodCategoryAdapter(FoodSectionActivity.this, foodMenu);
            categories.setAdapter(adapter);
            categories.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Log.e(TAG, "OnChildClickListener group=" + groupPosition + " child=" + childPosition);
                    Intent openOrderForm = new Intent(getApplicationContext(), OrderActivity.class);
                    Item item = (Item) foodMenu.getCategoryAt(groupPosition).getItemAt(childPosition);
                    openOrderForm.putExtra(Constants.KEY_SKU, item.sku);
                    openOrderForm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(openOrderForm);
                    return true;
                }
            });

            categories.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return false;
                }
            });
        }
    }

}


