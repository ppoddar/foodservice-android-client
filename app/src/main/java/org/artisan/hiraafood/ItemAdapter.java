package org.artisan.hiraafood;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import org.artisan.hiraafood.model.Item;
import org.artisan.hiraafood.view.ItemView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates a view for an item on multiple layouts.
 * An item in a list can be appear in
 *    1> ExpandableListView in menu.
 *    2> Recommendation
 *
 * @see ItemView
 */
public class ItemAdapter extends ArrayAdapter<Item> {
    private final ItemView.Type viewType;
    private final Context ctx;

    public ItemAdapter(@NonNull Context context,
                       ItemView.Type type,
                       @NonNull List<Item> objects) {
        super(context, ItemView.getLayoutFor(type), objects);
        ctx = context;
        viewType = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //Log.e(TAG, "getChildView() group=" + groupPosition + "child=" + childPosition + " isExpanded=" + isExpanded);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int layout = ItemView.getLayoutFor(viewType);
            convertView = inflater.inflate(layout, viewGroup, false);

        }
        Item item = (Item) getItem(position);
        ItemView.create(viewType, ctx).decorate((ViewGroup)convertView, item);

        return convertView;

    }

}
