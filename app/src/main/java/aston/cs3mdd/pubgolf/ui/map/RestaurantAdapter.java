package aston.cs3mdd.pubgolf.ui.map;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.ui.map.models.Restaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private ArrayList<Restaurant> restaurantList;

    private RestaurantClickListener restaurantClickListener;

    public RestaurantAdapter(ArrayList<Restaurant> restaurant, Context context, RestaurantClickListener restaurantClickListener) {
        this.restaurantList = restaurant;
        this.restaurantClickListener = restaurantClickListener;
    }

    public interface RestaurantClickListener {
        void selectedRestaurant(Restaurant restaurant);
    }

    public void filterList(ArrayList<Restaurant> filterList) {
        restaurantList = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant model = restaurantList.get(position);
        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.rating.setText(model.getRating());
        holder.totalRating.setText(model.getTotalRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantClickListener.selectedRestaurant(restaurantList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView address;
        private final TextView rating;
        private final TextView totalRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            rating = itemView.findViewById(R.id.rating);
            totalRating = itemView.findViewById(R.id.totalRating);
        }
    }

}
