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

public class RestaurantAdapter extends ArrayAdapter<Restaurant> implements Filterable {
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

    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Restaurant> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.values = getRestaurantListFilter;
                    filterResults.count = getRestaurantListFilter.size();
                } else {
                    String searchStr = constraint.toString().toLowerCase();
                    for (Restaurant restaurant : restaurantList) {
                        if (restaurant.getName().toString().contains(searchStr)) {
                            filteredList.add(restaurant);
                        }
                    }
                }
                filterResults.values = filteredList;
                filterResults.count = filteredList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                restaurantList = (List<Restaurant>) results.values;
                Log.i("AAAAA", results.values.toString());
                notifyDataSetChanged();
            }
        };
        return filter;
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
