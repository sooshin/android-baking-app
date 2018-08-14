package com.example.android.bakingapp.utilities;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.android.bakingapp.R;

import static com.example.android.bakingapp.utilities.Constant.NUM_POSITION_FOUR;
import static com.example.android.bakingapp.utilities.Constant.POSITION_ONE;
import static com.example.android.bakingapp.utilities.Constant.POSITION_THREE;
import static com.example.android.bakingapp.utilities.Constant.POSITION_TWO;
import static com.example.android.bakingapp.utilities.Constant.POSITION_ZERO;

public class BakingUtils {

    /**
     *  If the image URL of the recipe does not exist, set the background color of the ImageView
     *  differently depending on the position.
     *
     * @param context The context of the app
     * @param position The position of the recipe item
     * @return The background color of the ImageView
     */
    public static int getImageBackGroundColor(Context context, int position) {
        int imageColorResourceId;

        switch (position % NUM_POSITION_FOUR) {
            case POSITION_ZERO:
                imageColorResourceId = R.color.image_mint;
                break;
            case POSITION_ONE:
                imageColorResourceId = R.color.image_light_green;
                break;
            case POSITION_TWO:
                imageColorResourceId = R.color.image_yellow;
                break;
            case POSITION_THREE:
                imageColorResourceId = R.color.image_pink;
                break;
            default:
                imageColorResourceId = R.color.image_light_blue;
                break;
        }
        return ContextCompat.getColor(context, imageColorResourceId);
    }

    /**
     * If the image URL of the recipe does not exist, set the background color of the TextView
     * displaying the recipe name differently depending on the position.
     *
     * @param context The context of the app
     * @param position The position of the recipe item
     * @return The background color of the TextView displaying the recipe name
     */
    public static int getTextBackGroundColor(Context context, int position) {
        int textColorResourceId;
        switch (position % NUM_POSITION_FOUR) {
            case POSITION_ZERO:
                textColorResourceId = R.color.text_mint;
                break;
            case POSITION_ONE:
                textColorResourceId = R.color.text_light_green;
                break;
            case POSITION_TWO:
                textColorResourceId = R.color.text_yellow;
                break;
            case POSITION_THREE:
                textColorResourceId = R.color.text_pink;
                break;
            default:
                textColorResourceId = R.color.text_light_blue;
                break;
        }
        return ContextCompat.getColor(context, textColorResourceId);
    }

    /**
     * Returns the image resource used when the image URL of the recipe does not exist.
     *
     * @param position The position of the recipe item
     */
    public static int getImageResource(int position) {
        int imageResourceId;
        switch (position % NUM_POSITION_FOUR) {
            case POSITION_ZERO:
                imageResourceId = R.drawable.pie;
                break;
            case POSITION_ONE:
                imageResourceId = R.drawable.cake_with_berries;
                break;
            case POSITION_TWO:
                imageResourceId = R.drawable.valentine_cake;
                break;
            case POSITION_THREE:
                imageResourceId = R.drawable.cake_with_cherry;
                break;
            default:
                imageResourceId = R.drawable.cake_with_cherry;
                break;
        }
        return imageResourceId;
    }

}
