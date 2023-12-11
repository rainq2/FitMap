package com.example.sportsworlddemo2.homepage;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<String> {

    private Context mContext;

    public CustomArrayAdapter(Context context, String[] routes) {
        super(context, 0, routes);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String route = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        // Lookup view for data population
        TextView textView = convertView.findViewById(android.R.id.text1);
        // Populate the data into the template view using the data object
        textView.setText(route);
        // Set the text color to deep blue
        textView.setTextColor(Color.parseColor("#000000")); // 深黑
        // Return the completed view to render on screen
        return convertView;
    }
}
