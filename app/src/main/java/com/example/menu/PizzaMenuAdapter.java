package com.example.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class PizzaMenuAdapter extends RecyclerView.Adapter<PizzaMenuAdapter.MenuItemViewHolder> {

    private ArrayList<MenuItemData> menuList;

    public PizzaMenuAdapter(ArrayList<MenuItemData> menuItemList) {
        menuList = menuItemList;
    }

    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.menu_item, parent, false);
        MenuItemViewHolder itemViewHolder = new MenuItemViewHolder(listItem);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, int position) {
        MenuItemData item = menuList.get(position);
        holder.itemTitleView.setText(item.getName());

        Picasso.get().load(item.getImageUrl()).fit().into(holder.itemImageView);
        StringBuffer ingredientBuffer = new StringBuffer();
        for (int index = 0; index < item.getIngredients().size(); index++) {
            ingredientBuffer.append(", ");
            ingredientBuffer.append(item.getIngredients().get(index));
        }

        holder.itemIngredientsView.setText(ingredientBuffer.toString());
        holder.itemDiscountView.setText(item.getDiscount());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitleView;
        public ImageView itemImageView;
        public TextView itemIngredientsView;
        public TextView itemPriceView;
        public TextView itemDiscountView;

        public MenuItemViewHolder(View itemView) {
            super(itemView);
            itemTitleView = (TextView) itemView.findViewById(R.id.itemTitle);
            itemImageView = (ImageView) itemView.findViewById(R.id.itemImage);
            itemIngredientsView = (TextView) itemView.findViewById(R.id.ingredients);
            itemPriceView = (TextView) itemView.findViewById(R.id.price);
            itemDiscountView = (TextView) itemView.findViewById(R.id.discount);
        }
    }
}


