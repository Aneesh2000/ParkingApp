package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.concurrent.Executor;

public class AirplaneAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {
    private OnCarSelected mOnCarSelected;
    FirebaseFirestore fStore;
    private static class NormalViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCar;
        private final ImageView imgCarSelected;


        public NormalViewHolder(View itemView) {
            super(itemView);
            imgCar = (ImageView) itemView.findViewById(R.id.img_Car);
            imgCarSelected = (ImageView) itemView.findViewById(R.id.img_Car_selected);

        }

    }

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private List<CarItem> mItems;

    public AirplaneAdapter(Context context, List<CarItem> items) {
        mOnCarSelected = (OnCarSelected) context;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = mLayoutInflater.inflate(R.layout.list_item_car, parent, false);
            return new NormalViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        //int type = mItems.get(position).getType();

            final NormalItem item = (NormalItem) mItems.get(position);
            NormalViewHolder holder = (NormalViewHolder) viewHolder;


            holder.imgCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    toggleSelection(position);

                    mOnCarSelected.onCarSelected(getSelectedItemCount());
                }
            });

            holder.imgCarSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);


    }

}
