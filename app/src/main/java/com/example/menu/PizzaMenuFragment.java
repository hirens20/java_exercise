package com.example.menu;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.databinding.FragmentPizzaMenuBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PizzaMenuFragment extends Fragment {

    private ArrayList<MenuItemData> menuInfoList;
    private FragmentPizzaMenuBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPizzaMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler handler = new Handler(Looper.getMainLooper());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            processJson();
            handler.post(() -> {
               binding.menuListView.setAdapter(new PizzaMenuAdapter(menuInfoList));
            });
        });
    }


    private String loadData() {
        try {
            InputStream stream = getActivity().getAssets().open("menuData.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            return new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "";
    }

    private void processJson() {
        String readData = loadData();
        menuInfoList = new ArrayList<MenuItemData>();
        if (!readData.isEmpty()) {
            try {
                JSONArray menuArray = new JSONArray(readData);
                String name = "";
                for (int index = 0; index < menuArray.length(); index++) {
                    JSONObject menuItem = menuArray.getJSONObject(index);
                    name = menuItem.getString("name");

                    ArrayList<String> ingredientArray = new ArrayList<String>();
                    if (menuItem.has("ingredients")) {
                        JSONArray ingredientJsonArray = menuItem.getJSONArray("ingredients");
                        for (int ingredientIndex = 0; index < ingredientJsonArray.length(); index++) {
                            ingredientArray.add(ingredientJsonArray.getString(ingredientIndex));
                        }
                    }

                    String lowResImage = null;
                    if (menuItem.has("images")) {
                        JSONObject images = menuItem.getJSONObject("images");
                        if (images.has("lowResImage")) {
                            lowResImage = images.getString("lowResImage");
                        }
                    }

                    String price = null;
                    if (menuItem.has("price")) {
                        price = menuItem.getString("price");
                    }

                    String discount = null;
                    if (menuItem.has("discountPercent")) {
                        discount = menuItem.getString("discountPercent");
                    }

                    menuInfoList.add(
                            new MenuItemData(
                                    name,
                                    ingredientArray,
                                    lowResImage,
                                    price,
                                    discount
                            )
                    );
                }
            } catch (JSONException jex) {
                menuInfoList = null;
            }
        }
    }
}
