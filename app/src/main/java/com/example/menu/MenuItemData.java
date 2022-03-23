package com.example.menu;

import java.util.ArrayList;

public class MenuItemData {
    private String name;
    private ArrayList<String> ingredients;
    private String imageUrl;
    private String price;
    private String discount;

    public MenuItemData(String name, ArrayList<String> ingredients, String imageUrl, String price, String discount) {
        this.name = name;
        this.ingredients = ingredients;
        this.imageUrl = imageUrl;
        this.price = price;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

}