package com.example.javven.pubmatictest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {

  private List<Vehicle> vehicles = new ArrayList<>();
  private RecyclerView rv;
  private ListAdapter listAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);
    rv = findViewById(R.id.rv);

    Vehicle vehicle = new Vehicle();

    for (int i = 0; i < 2000; i++) {
      vehicles.add(vehicle);
    }

    listAdapter = new ListAdapter();
    listAdapter.addAll(vehicles);
    rv.setAdapter(listAdapter);
  }
}
