package org.artisan.hiraafood;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;

import com.google.android.material.appbar.MaterialToolbar;

import org.artisan.hiraafood.model.Cart;

/**
 * Updates the Cart icon on the toolbar when the cart state changes.
 */
class CartObserver implements Observer<Cart> {
    private final Context ctx;
    private final ViewGroup container;
    public CartObserver(Activity ctx, ViewGroup container) {
        this.ctx = ctx;
        this.container = container;
    }
    @Override
    public void onChanged(Cart cart) {
        MaterialToolbar toolbar = container.findViewById(R.id.toolbar_main);
        TextView cartView = toolbar.findViewById(R.id.action_cart);
        int count = cart.getItemCount();
        Resources res = ctx.getResources();
        Drawable background = ResourcesCompat.getDrawable(res,
                (count == 0 ? R.drawable.icon_cart_empty : R.drawable.icon_cart), null);
        cartView.setBackground(background);
        cartView.setText(count == 0 ? "" : Integer.toString(count));
    }
}
