package com.everlapp.androidexamples.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 *
 *
 * https://developer.android.com/training/material/lists-cards.html
 */
public class SimpleRecyclerAdapter
        extends RecyclerView.Adapter<SimpleRecyclerAdapter.SimpleViewHolder> {

    /**
     * Interface for click listener. Implement in Activity or Fragment
     */
    public interface OnItemClickListener {
        void onItemClick();
    }

    private OnItemClickListener mClickListener;

    public SimpleRecyclerAdapter(OnItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Если View разных типов
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }



    class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle;
        private TextView mText;

        public void setTitle(TextView title) {
            this.mTitle = title;
        }

        public void setText(TextView text) {
            this.mText = text;
        }


        public SimpleViewHolder(View itemView) {
            super(itemView);

            // mTitle = findViewById
            // mText = findViewById
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick();
        }
    }


}

