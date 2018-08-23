/*
 *  Copyright 2018 Soojeong Shin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
import com.squareup.picasso.Picasso;

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
        holder.bind(step, position);
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
         * This method will take a step object and position as input and use that step and position
         * to display the step ID and the short description within a list item. If the thumbnail URL
         * exists, display the thumbnail image of the step.
         *
         * @param step The step object
         * @param position The position of the item within the adapter's data set.
         */
        void bind(Step step, int position) {
            // Get the step ID that matches to the step index.
            // (e.g. Step ID of Yellow cake from  8 to 13 does not match to the position)
            int stepId = getCorrectStepId(step, position);
            // Set the step ID
            mStepsItemBinding.tvStepId.setText(String.valueOf(stepId));
            // Set the short description
            mStepsItemBinding.tvStepShortDescription.setText(step.getShortDescription());

            String thumbnailUrl = step.getThumbnailUrl();
            // Check if the thumbnail has a valid URL.
            // (e.g. The Step 5 of Nutella Pie has an "mp4" in the thumbnail URL)
            if (thumbnailUrl.isEmpty() || thumbnailUrl.contains(
                    itemView.getContext().getString(R.string.mp4))) {
                // Hide ImageView thumbnail
                mStepsItemBinding.ivThumbnail.setVisibility(View.GONE);
            } else {
                // If the thumbnail URL exists, make sure ImageView visible and
                // use the Picasso library to upload the thumbnail
                mStepsItemBinding.ivThumbnail.setVisibility(View.VISIBLE);
                Picasso.with(itemView.getContext())
                        .load(thumbnailUrl)
                        .error(R.drawable.recipe_error_image)
                        .placeholder(R.drawable.recipe_error_image)
                        .into(mStepsItemBinding.ivThumbnail);
            }
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

        /**
         * Returns step ID that matches to the step index.
         *
         * @param step The step object
         * @param position The position of the item within the adapter's data set.
         */
        private int getCorrectStepId(Step step, int position) {
            int stepId = step.getStepId();
            // If the step ID does not correspond to the step index, replace step ID with
            // the step index.
            if (stepId != position) {
                stepId = position;
            }
            return stepId;
        }

    }
}
