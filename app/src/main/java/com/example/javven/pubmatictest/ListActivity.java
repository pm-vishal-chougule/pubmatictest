package com.example.javven.pubmatictest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {

  private List<ListItem> vehicles = new ArrayList<>();
  private RecyclerView rv;
  private ListAdapter listAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);
    rv = findViewById(R.id.rv);

    rv.setLayoutManager(new LinearLayoutManager(this));
    rv.setHasFixedSize(true);



    for (int i = 0; i < 200; i++) {
      Vehicle vehicle = new Vehicle();
      vehicle.price = String.valueOf(i+1);
      vehicles.add(vehicle);
    }

    listAdapter = new ListAdapter(this);
    listAdapter.addAll(vehicles);
    rv.setAdapter(listAdapter);
  }
}
