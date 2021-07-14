package org.artisan.hiraafood.server;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.artisan.hiraafood.model.Cart;
import org.artisan.hiraafood.model.FoodMenu;
import org.artisan.hiraafood.model.FoodMenuBuilder;
import org.artisan.hiraafood.model.RecommendationBuilder;
import org.artisan.hiraafood.model.Recommendations;

import java.io.InputStream;

public class FoodServiceDataModel extends ViewModel {
    private MutableLiveData<FoodMenu> menu;
    private MutableLiveData<Recommendations> recommendations;
    private MutableLiveData<Cart> cart;
    private Context ctx;
    private static FoodServiceDataModel singleton;

    public static FoodServiceDataModel getInstance(Context ctx) {
        if (singleton == null) {
            singleton = new FoodServiceDataModel(ctx);
        }
        return singleton;
    }

    private FoodServiceDataModel(Context ctx) {
        this.ctx = ctx;

    }
    public MutableLiveData<Cart> getCart() {
        if (cart == null) {
            cart = new MutableLiveData<>(new Cart());
        }
        return cart;
    }

    public MutableLiveData<FoodMenu> getMenu() {
        if (menu == null) {
            try {
                InputStream in = ctx.getResources().getAssets().open("menu.json");
                FoodMenu data = FoodMenuBuilder.build(in);
                menu = new MutableLiveData<>(data);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return menu;
    }
    public MutableLiveData<Recommendations> getRecommendations() {
        if (menu == null) {
            try {
                InputStream in = ctx.getResources().getAssets().open("menu.json");
                Recommendations data = RecommendationBuilder.build(in);
                recommendations = new MutableLiveData<>(data);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return recommendations;
    }

}
