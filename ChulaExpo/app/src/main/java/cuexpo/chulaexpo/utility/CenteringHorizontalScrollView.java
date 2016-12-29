package cuexpo.chulaexpo.utility;

/**
 * Created by APTX-4869 (LOCAL) on 12/29/2016.
 */

import android.content.Context;
import android.content.Loader;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CenteringHorizontalScrollView extends HorizontalScrollView implements View.OnTouchListener {

    private Context mContext;
    private static final int SWIPE_PAGE_ON_FACTOR = 10;
    private int mActiveItem;
    private float mPrevScrollX;
    private boolean mStart;
    private int mItemWidth;

    View targetLeft, targetRight;
    ImageView leftImage, rightImage;

    public CenteringHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mItemWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
//        mActiveItem = 0;
//        setCurrentItemAndCenter(mActiveItem);
//        centerCurrentItem();
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getRawX();
        boolean handled = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mStart) {
                    mPrevScrollX = x;
                    mStart = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mStart = true;
                int minFactor = mItemWidth / SWIPE_PAGE_ON_FACTOR;
                if ((mPrevScrollX - (float) x) > minFactor) {
                    if (mActiveItem < getMaxItemCount() - 1) {
                        mActiveItem = mActiveItem + 1;
                    }
                }
                else if (((float) x - mPrevScrollX) > minFactor) {
                    if (mActiveItem > 0) {
                        mActiveItem = mActiveItem - 1;
                    }
                }
                scrollToActiveItem();
                handled = true;
                break;
        }
        return handled;
    }

    private int getMaxItemCount() {
        return ((LinearLayout) getChildAt(0)).getChildCount();
    }

    private LinearLayout getLinearLayout() {
        return (LinearLayout) getChildAt(0);
    }

    /**
     * Centers the current view the best it can.
     */
    public void centerCurrentItem() {
        if (getMaxItemCount() == 0) {
            return;
        }
        int currentX = getScrollX();
        View targetChild;
        int currentChild = -1;
        do {
            currentChild++;
            targetChild = getLinearLayout().getChildAt(currentChild);
        } while (currentChild < getMaxItemCount() && targetChild.getLeft() < currentX);
        if (mActiveItem != currentChild) {
            mActiveItem = currentChild;
            scrollToActiveItem();
        }
    }

    /**
     * Scrolls the list view to the currently active child.
     */
    private void scrollToActiveItem() {
        int maxItemCount = getMaxItemCount();
        if (maxItemCount == 0) {
            return;
        }
        int targetItem = Math.min(maxItemCount - 1, mActiveItem);
        targetItem = Math.max(0, targetItem);
        mActiveItem = targetItem;

        // Scroll so that the target child is centered
        View targetView = getLinearLayout().getChildAt(targetItem);

        // Center
        ImageView centerImage = (ImageView)targetView;
        int center_img_width = dpToPx(272);
        int center_img_height = dpToPx(272);
        LinearLayout.LayoutParams flparams = new LinearLayout.LayoutParams(center_img_width, center_img_height);
        centerImage.setLayoutParams(flparams);

        // Left
        int side_img_width = dpToPx(170);
        int side_img_height = dpToPx(170);
        if((targetItem-1) >= 0){
            targetLeft = getLinearLayout().getChildAt(targetItem-1);
            leftImage = (ImageView)targetLeft;
            LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(side_img_width, side_img_height);
            leftParams.setMargins(0, dpToPx(51), 0, 0);
            leftImage.setLayoutParams(leftParams);
        }

        // Right
        if((targetItem+1) < maxItemCount){
            targetRight = getLinearLayout().getChildAt(targetItem+1);
            rightImage = (ImageView)targetRight;
            LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(side_img_width, side_img_height);
            rightParams.setMargins(0, dpToPx(51), 0, 0);
            rightImage.setLayoutParams(rightParams);
        }

//        int targetLeft = targetView.getLeft();
//        Log.d("target left", "" + targetLeft);
//        int childWidth = targetView.getRight() - targetLeft;
//        Log.d("child width", "" + childWidth);

//        int width = getWidth() - getPaddingLeft() - getPaddingRight();
//        int targetScroll = targetLeft - ((width - childWidth) / 2);
//        Log.d("target scroll", "" + targetScroll);
//        super.smoothScrollTo(targetScroll, 0);
        super.smoothScrollTo((429*mActiveItem) - 197, 0);
    }

    /**
     * Sets the current item and centers it.
     * @param currentItem The new current item.
     */
    public void setCurrentItemAndCenter(int currentItem) {
        mActiveItem = currentItem;
        scrollToActiveItem();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
