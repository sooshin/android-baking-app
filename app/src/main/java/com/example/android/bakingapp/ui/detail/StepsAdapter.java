package com.example.android.bakingapp.ui.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.StepsListItemBinding;
import com.example.android.bakingapp.model.Step;

import java.util.List;

/**
 * {@link StepsAdapter} exposes a list of steps to a {@link RecyclerView}
 */
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    /** Member variable for the list of {@link Step}s */
    private List<Step> mSteps;

    /** An on-click handler that we've defined to make it easy for a Fragment to interface with
     * our RecyclerView
     */
    private final StepsAdapterOnClickHandler mOnClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface StepsAdapterOnClickHandler {
        void onItemClick(int stepIndex);
    }

    /**
     * Constructor for StepsAdapter that accepts a list of steps to display
     *
     * @param steps The list of {@link Step}s
     * @param onClickHandler The on-click handler for this adapter. This single handler is called
     *                       when an item is clicked.
     */
    public StepsAdapter(List<Step> steps, StepsAdapterOnClickHandler onClickHandler) {
        mSteps = steps;
        mOnClickHandler = onClickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent The ViewGroup that these ViewHolders are contained within.
     * @param viewType If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout.
     * @return A new StepsViewHolder that holds the StepsListItemBinding
     */
    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        StepsListItemBinding stepsItemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.steps_list_item, parent, false);
        return new StepsViewHolder(stepsItemBinding);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        Step step = mSteps.get(position);
        holder.bind(step);
    }

    /**
     * This method simply return the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of steps
     */
    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size();
    }

    /**
     * This method is to add a list of {@link Step}s
     *
     * @param steps Steps is the the data source of the adapter
     */
    public void addAll(List<Step> steps) {
        mSteps.clear();
        mSteps.addAll(steps);
        notifyDataSetChanged();
    }

    /**
     * Cache of the children views for a step list item.
     */
    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /** This field is used for data binding */
        private StepsListItemBinding mStepsItemBinding;


        /**
         * Constructor for our ViewHolder
         *
         * @param stepsItemBinding Used to access the layout's variables and views
         */
        public StepsViewHolder(StepsListItemBinding stepsItemBinding) {
            super(stepsItemBinding.getRoot());
            mStepsItemBinding = stepsItemBinding;
            // Call setOnClickListener on the View
            itemView.setOnClickListener(this);
        }

        /**
         * This method will take a Step object as input and use that Step to display the short description
         * within a list item
         *
         * @param step The step object
         */
        void bind(Step step) {
            String stepId = itemView.getContext().getString(R.string.step) + step.getStepId();
            mStepsItemBinding.tvStepId.setText(stepId);
            mStepsItemBinding.tvStepShortDescription.setText(step.getShortDescription());
        }

        /**
         * Called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mOnClickHandler.onItemClick(adapterPosition);
        }
    }
}
