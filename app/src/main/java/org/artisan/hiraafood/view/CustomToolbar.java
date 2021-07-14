package org.artisan.hiraafood.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;

import org.artisan.hiraafood.R;
import org.artisan.hiraafood.model.Cart;

/**
 * A custom view for an application specific toolbar.
 * Extends a {@link CoordinatorLayout} and inflates a layout inside it.
 * The layout is specified as a resource <code>@layout/...</code> in
 * XML layout speication for this widget. The layout typically has
 * a MaterialToolbar (see <code>@layout/toolbar_custom.xml</code>
 * for an example)
 */
public class CustomToolbar extends CoordinatorLayout {
    /**
     * Platform defiend constructor for a View.
     * The layout specified in the attribute is inflated
     * and added to this {@link android.view.ViewGroup layout}.
     *
     * @param context a context
     * @param attrs set of attributes in XML layout spec
     *              the attribute named layout is inflated.
     */
    public CustomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomToolbar, 0, 0);

        @LayoutRes int layout = a.getResourceId(R.styleable.CustomToolbar_layout, -1);
        a.recycle();
        if (layout == -1) {
            throw new IllegalArgumentException("layout attribute in not specified in XML layout of " + this.getClass().getName());
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layout, this);
    }

    /**
     * Gets an observer to reflect any change on the cart. The cart appears
     * as an icon in the toolbar.
     *
     * @param ctx a context
     * @return an observer
     */
    public CartObserver newCartObserver(Activity ctx) {
        return new CartObserver(ctx);
    }

    /**
     * An observer that is notified on any change in the {@link Cart cart}
     * and reflects in the UI.
     */
    class CartObserver implements Observer<Cart> {
        private final Context ctx;

        public CartObserver(Activity ctx) {
            this.ctx = ctx;
        }

        @Override
        public void onChanged(Cart cart) {
            TextView cartView = findViewById(R.id.action_cart);
            int count = cart.getItemCount();
            Resources res = ctx.getResources();
            Drawable background = ResourcesCompat.getDrawable(res,
                    (count == 0 ? R.drawable.icon_cart_empty : R.drawable.icon_cart), null);
            cartView.setBackground(background);
            cartView.setText(count == 0 ? "" : Integer.toString(count));
        }
    }


}
