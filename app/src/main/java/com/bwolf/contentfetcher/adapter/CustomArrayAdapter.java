package com.bwolf.contentfetcher.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.bwolf.contentfetcher.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<HashMap<String, String>> {
  private final Context context;
  private final ArrayList<HashMap<String, String>> values;

  public CustomArrayAdapter(Context context, ArrayList<HashMap<String, String>> values) {
    super(context, -1, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.custom_adapter, parent, false);
    
    TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
    TextView textView2 = (TextView) rowView.findViewById(R.id.secondLine);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
    textView.setText(values.get(position).get("id"));
    textView2.setText(values.get(position).get("name"));

    

    return rowView;
  }
} 


