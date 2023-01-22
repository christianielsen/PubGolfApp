package aston.cs3mdd.pubgolf.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import aston.cs3mdd.pubgolf.R;
import aston.cs3mdd.pubgolf.ui.map.placemodels.Restaurant;

/*
* Adapter class for the RecyclerView
* */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private ArrayList<Restaurant> restaurantList;

    private RestaurantClickListener restaurantClickListener;

    public RestaurantAdapter(ArrayList<Restaurant> restaurant, Context context, RestaurantClickListener restaurantClickListener) {
        this.restaurantList = restaurant;
        this.restaurantClickListener = restaurantClickListener;
    }

    //Interface listener for the clickable items in the recyclerview
    public interface RestaurantClickListener {
        void selectedRestaurant(Restaurant restaurant);
    }

    //Change the original arraylist to the temporary arraylist
    public void filterList(ArrayList<Restaurant> filterList) {
        restaurantList = filterList;
        //Refresh the recyclerview
        notifyDataSetChanged();
    }

    //Use the layout for each item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    //Set the data into the text views
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant model = restaurantList.get(position);
        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.rating.setText(model.getRating());
        holder.totalRating.setText(model.getTotalRating());
        holder.isOpen.setText(model.getIsOpen());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantClickListener.selectedRestaurant(restaurantList.get(holder.getBindingAdapterPosition()));
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
        private final TextView isOpen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            rating = itemView.findViewById(R.id.rating);
            totalRating = itemView.findViewById(R.id.totalRating);
            isOpen = itemView.findViewById(R.id.isOpen);
        }
    }

}
