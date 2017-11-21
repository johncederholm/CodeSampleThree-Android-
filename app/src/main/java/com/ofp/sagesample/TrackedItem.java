package com.ofp.sagesample;

import android.content.Context;
import android.util.Log;

import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

/**
 * Created by johncederholm on 11/20/17.
 */

public class TrackedItem extends RealmObject {
    public String title;
    public String description;
    public String secondDescription;
    public Integer amount;
    public String imageUrl;
    public String type;

    public static ArrayList<TrackedItem> getItemsFromFile(String filename, Context context){
        final ArrayList<TrackedItem> itemList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset("trackedItems.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray items = json.getJSONArray("trackedItems");

            // Get Item objects from data
            for(int i = 0; i < items.length(); i++){
                TrackedItem item = new TrackedItem();

                item.title = items.getJSONObject(i).getString("title");
                item.description = items.getJSONObject(i).getString("description");
                item.secondDescription = items.getJSONObject(i).getString("secondDescription");
                item.imageUrl = items.getJSONObject(i).getString("image");
                item.amount = items.getJSONObject(i).getInt("amount");
                item.type = items.getJSONObject(i).getString("type");
                itemList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
