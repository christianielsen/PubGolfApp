package aston.cs3mdd.pubgolf.ui.map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.ui.map.models.Restaurant;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private List<Restaurant> restaurantList;
    private List<Restaurant> getRestaurantListFilter = new ArrayList<>();
    Context mContext;

    private static class ViewHolder {
        TextView name;
        TextView address;
        TextView rating;
    }

    public RestaurantAdapter(List<Restaurant> restaurant, Context context) {
        super(context, R.layout.row_layout, restaurant);
        this.restaurantList = restaurant;
        this.getRestaurantListFilter = restaurant;
        this.mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Restaurant restaurant = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.rating);

            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.name.setText(restaurant.getName());
        viewHolder.address.setText(restaurant.getAddress());
        viewHolder.rating.setText(restaurant.getRating());


        return convertView;
    }


}
