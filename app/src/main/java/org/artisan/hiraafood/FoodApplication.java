package org.artisan.hiraafood;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.artisan.hiraafood.model.Cart;
import org.artisan.hiraafood.model.FoodMenu;
import org.artisan.hiraafood.model.FoodMenuBuilder;
import org.artisan.hiraafood.model.RecommendationBuilder;
import org.artisan.hiraafood.model.Recommendations;
import org.artisan.hiraafood.server.FoodServiceDataModel;
import org.artisan.hiraafood.server.LocalFoodService;

public class FoodApplication extends Application  {
    FoodServiceDataModel model;
    @Override
    public void onCreate() {
        super.onCreate();
        model = FoodServiceDataModel.getInstance(this);
    }

    public FoodServiceDataModel getDataModel() {
        return model;
    }
}
