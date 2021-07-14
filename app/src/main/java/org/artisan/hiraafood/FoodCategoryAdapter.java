package org.artisan.hiraafood;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.artisan.hiraafood.model.Cart;
import org.artisan.hiraafood.model.FoodCategory;
import org.artisan.hiraafood.model.FoodMenu;
import org.artisan.hiraafood.model.Item;
import org.artisan.hiraafood.model.LineItem;
import org.artisan.hiraafood.view.ItemView;

public class FoodCategoryAdapter extends BaseExpandableListAdapter {
    private static final String TAG = FoodCategoryAdapter.class.getSimpleName();
    FoodMenu menu;
    private final Activity ctx;

    public FoodCategoryAdapter(Activity ctx,
                               FoodMenu menu) {
        super();
        this.ctx = ctx;
        this.menu = menu;
    }

    /**
     * determines if all groups are expanded initially.
     *
     * @return what does it mean? // TODO
     */
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public int getGroupCount() {
        return menu.getCategoryCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((FoodCategory) menu.getCategoryAt(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return menu.getCategoryAt(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ((FoodCategory) menu.getCategoryAt(groupPosition)).getItemAt(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return getGroup(groupPosition).hashCode();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getChild(groupPosition, childPosition).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = ctx.getLayoutInflater()
                    .inflate(R.layout.food_category_template, parent, false);
        }
        Log.e(TAG, "getGroupView() idx=" + groupPosition + " isExpanded:" + isExpanded);
        TextView categoryLabel = convertView.findViewById(R.id.food_category);
        FoodCategory category = menu.getCategoryAt(groupPosition);
        categoryLabel.setText(category.getName());
        categoryLabel.setCompoundDrawables(
                ctx.getDrawable(isExpanded ? R.drawable.icon_expanded : R.drawable.icon_collapsed),
                null, null, null);
        final ImageButton categoryExpand = convertView.findViewById(R.id.button_category_expand);
        categoryExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpandableListView list = (ExpandableListView)parent;
                if (isExpanded) {
                    list.collapseGroup(groupPosition);
                    categoryExpand.setBackground(parent.getResources()
                            .getDrawable(R.drawable.icon_collapsed, null));
                } else {
                    list.expandGroup(groupPosition);
                    categoryExpand.setBackground(parent.getResources()
                            .getDrawable(R.drawable.icon_expanded, null));
                }
            }
        });
        //((ExpandableListView)parent).expandGroup(groupPosition);
        return convertView;
    }


    /**
     * View of an item. Attaches an 'order' button with the core view of an Item.
     * The button on click adds order for the corresponding item with single quantity.
     * On clicking the item itself, a new OrderForm is opened.
     *
     * @param groupPosition position of the group
     * @param childPosition position of the item
     * @param isExpanded true if expanded
     * @param convertView view
     * @param viewGroup parent
     * @return child view
     */
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isExpanded,
                             View convertView, ViewGroup viewGroup) {
        Log.e(TAG, "getChildView() group=" + groupPosition + "child=" + childPosition + " isExpanded=" + isExpanded);
        final Item item = (Item) getChild(groupPosition, childPosition);
        ItemView itemView = ItemView.create(ItemView.Type.ExpandableList, ctx);
        if (convertView == null) {
            convertView = itemView.create(R.id.item, item);
        } else {
            itemView.decorate((ViewGroup) convertView, item);
        }

        Button orderButton = convertView.findViewById(R.id.action_order);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodApplication app = (FoodApplication) ctx.getApplication();
                Cart cart = app.getDataModel().getCart().getValue();
                if (cart != null) cart.addLineItem(new LineItem(item));
                // resetting the cart value produces the livedata notification
                app.getDataModel().getCart().setValue(cart);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


}
