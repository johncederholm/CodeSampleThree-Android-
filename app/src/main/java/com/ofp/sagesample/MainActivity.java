package com.ofp.sagesample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.item_list_view);
        resetTable();
    }

    private void resetTable() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SAGESample");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    final ArrayList<TrackedItem> itemList = new ArrayList<>();
                    for (ParseObject obj : objects) {
                        TrackedItem item = new TrackedItem();
                        item.title = (String) obj.get("Name");
                        item.description = (String) obj.get("description");
                        item.secondDescription = (String) obj.get("secondDescription");
                        item.imageUrl = (String) obj.get("imagePointer");
                        item.amount = (Integer) obj.get("value");
                        item.type = (String) obj.get("type");
                        itemList.add(item);
                    }
                    instantiateAdapter(itemList);
                }

            }
        });
    };

    private void instantiateAdapter(ArrayList<TrackedItem> itemList) {
        ItemAdapter adapter = new ItemAdapter(this, itemList);
        mListView.setAdapter(adapter);
        return;
    }

    private ListView mListView;

}
