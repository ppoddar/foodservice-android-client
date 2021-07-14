package org.artisan.hiraafood.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import org.artisan.hiraafood.R;
import org.artisan.hiraafood.model.Item;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Decorates a view group given an item. Produces different view based on different layouts.
 */
public class ItemView {
    final Context ctx;
    final Type type;

    public static ItemView create(ItemView.Type type, Context ctx) {
        return new ItemView(type, ctx);
    }

    private ItemView(ItemView.Type type, Context ctx) {
        this.ctx = ctx;
        this.type = type;
    }
    private static final Map<ItemView.Type, Integer> layouts = new HashMap<>();

    static {
        layouts.put(ItemView.Type.ExpandableList, R.layout.item_list_template);
        layouts.put(ItemView.Type.RecommendationList, R.layout.item_recommendation);
        layouts.put(ItemView.Type.OrderForm, R.layout.item_full);
    }

    /**
     * Gets the id of a layout resource for given type of view
     * @return layout resource id
     */
    public static int getLayoutFor(Type t) {
        return layouts.get(t);
    }

    /**
     * Creates a View inflating a layout.
     *
     * @param viewId the resource id for a view group in the layout to be populated.
     *               This viewId must be found in that inflated layout. That
     *               view group will be populated.
     * @return a view group
     */
    public ViewGroup create(@IdRes int viewId) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @LayoutRes int layout = layouts.get(type);
        View view = inflater.inflate(layout, null, false);
        ViewGroup viewGroup = view.findViewById(viewId);
        return viewGroup;

    }

    /**
     * Creates a ViewGroup inflating a layout and populates with data.
     *  @param viewId the resource id for a view group in the layout to be populated.
     *               This viewId must be found in that inflated layout. That
     *               view group will be populated.
     * @param item an item that serves as a model for the view
     */
    @NonNull
    public ViewGroup create(@IdRes int viewId, Item item) {
        ViewGroup viewGroup = create(viewId);
        decorateByViewType(viewGroup, item);
        return viewGroup;
    }

    /**
     * Decorates/populates given view group with given Item data.
     * There are several representation (view) possible for an Item.
     * The type determines the specific view.
     *
     * @param viewGroup teh view group to be populated
     * @param item the data
     */

    public void decorate(@NonNull ViewGroup viewGroup, @NonNull Item item) {
        decorateByViewType(viewGroup, item);
    }

    void decorateByViewType(@NonNull ViewGroup viewGroup, @NonNull Item item) {
        switch (type) {
            case RecommendationList:
                decorateForRecommendation(viewGroup, item);
                break;
            case ExpandableList:
                decorateForList(viewGroup, item);
                break;
            case OrderForm:
                decorateForOrderForm(viewGroup, item);
                break;
        }
    }


    /**
     * name
     * @param viewGroup view group to be decorated
     * @param item model
     */
    private void decorateForList(@NonNull ViewGroup viewGroup,
                                        @NonNull Item item) {
        TextView name  = viewGroup.findViewById(R.id.item_name);
        TextView price = viewGroup.findViewById(R.id.item_price);

        name.setText(item.name);
        price.setText(makeString(item.price));
        ImageView imageView = viewGroup.findViewById(R.id.item_image);
        Bitmap bitmap = getBitmap(ctx, item.image);
        if (bitmap != null) imageView.setImageBitmap(bitmap);
    }

    private void decorateForRecommendation(ViewGroup viewGroup, Item item) {
        CheckBox ordered  = viewGroup.findViewById(R.id.item_ordered);
        ordered.setText(item.name);
    }

    private void decorateForOrderForm(ViewGroup viewGroup, Item item) {
        CheckBox ordered  = viewGroup.findViewById(R.id.item_ordered);
        ordered.setText(item.name);
    }

    /**
     * Different types of view for an item
     */
    public enum Type {
        ExpandableList,      // appears in a menu list. shows a thumbnail image, name, price and rating
        RecommendationList,  // appears in recommendation list, shows name with a checkbox
        OrderForm            // appears in an order form. shows everything about an item
    }

    static String makeString(double price) {
        return String.format(Locale.getDefault(), "\u20A8 %6.2f", price);
    }

    static Bitmap getBitmap(Context ctx, String src) {
        try {
            InputStream is = ctx.getResources().getAssets().open(src);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
