package it.xabaras.android.viewpagerindicator.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.RadioGroup;

import it.xabaras.android.viewpagerindicator.R;

/**
 * Created by Paolo Montalto on 19/07/16.
 */

/**
 * This is simple page indicator for android ViewPager.
 */
public class ViewPagerIndicator extends RadioGroup {
    private static final String TAG = "ViewPagerIndicator";
    private ViewPager mViewPager;
    private int mItemDividerWidth;
    private Drawable mButtonDrawable;
    private int mItemRadius;
    private int mItemSelectedColor;
    private int mItemUnselectedColor;

    /**
     * Default Constructor
     * @param context a Context
     */
    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    /**
     * Constructor with attributes
     * @param context a Context
     * @param attrs an Attribute set
     */
    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        try {
            setOrientation(HORIZONTAL);
            setGravity(Gravity.CENTER);

            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.ViewPagerIndicator,
                    0, 0);

            mItemRadius = getResources().getDimensionPixelSize(R.dimen.default_item_radius);
            mItemRadius = a.getDimensionPixelSize(R.styleable.ViewPagerIndicator_itemRadius, mItemRadius);
            mItemDividerWidth = a.getDimensionPixelSize(R.styleable.ViewPagerIndicator_itemDividerWidth, getResources().getDimensionPixelSize(R.dimen.default_item_divider_width));
            int theme = a.getInt(R.styleable.ViewPagerIndicator_defaultIndicatorTheme, 0);
            if ( theme == 0 ) {
                mItemSelectedColor = ContextCompat.getColor(getContext(), R.color.default_indicator_on);
                mItemUnselectedColor = ContextCompat.getColor(getContext(), R.color.default_indicator_off);
            } else {
                mItemSelectedColor = ContextCompat.getColor(getContext(), R.color.default_indicator_light_on);
                mItemUnselectedColor = ContextCompat.getColor(getContext(), R.color.default_indicator_light_off);
            }

            mItemSelectedColor = a.getColor(R.styleable.ViewPagerIndicator_itemSelectedColor, mItemSelectedColor);
            mItemUnselectedColor = a.getColor(R.styleable.ViewPagerIndicator_itemUnselectedColor, mItemUnselectedColor);

            mButtonDrawable = getSelectorDrawable();

            int drawableResId = a.getResourceId(R.styleable.ViewPagerIndicator_pagerIndicatorDrawable, 0);
            if (drawableResId != 0 ) {
                mButtonDrawable = ContextCompat.getDrawable(getContext(), drawableResId);
            }
        } catch(Exception e) {
            Log.e(TAG, getMessageFor(e));
        }


    }

    /**
     * Create a StateListDrawable for the pager indicator
     *
     * @return a proper StateListDrawable
     */
    private StateListDrawable getSelectorDrawable() {
        StateListDrawable d = null;

        try {
            d = new StateListDrawable();
            ShapeDrawable selectedDrawable = new ShapeDrawable(new OvalShape());
            selectedDrawable.getPaint().setColor(mItemSelectedColor);
            selectedDrawable.setIntrinsicHeight(mItemRadius * 2);
            selectedDrawable.setIntrinsicWidth(mItemRadius * 2);
            ShapeDrawable unselectedDrawable = new ShapeDrawable(new OvalShape());
            unselectedDrawable.getPaint().setColor(mItemUnselectedColor);
            unselectedDrawable.setIntrinsicHeight(mItemRadius * 2);
            unselectedDrawable.setIntrinsicWidth(mItemRadius * 2);

            d.addState(new int[]{android.R.attr.state_checked}, selectedDrawable);
            d.addState(new int[]{}, unselectedDrawable);
        } catch(Exception e) {
            Log.e(TAG, getMessageFor(e));
        }

        return d;
    }

    /**
     * Initialize ViewPagerIndicator with a properly set up ViewPager
     * @param viewPager an instance of android.support.v4.view.ViewPager
     * @throws IllegalStateException if no adapter has been provided to the viewPager
     */
    public void initWithViewPager(ViewPager viewPager) throws IllegalStateException {
        if ( viewPager == null ) return;

        if ( viewPager.getAdapter() == null ) throw new IllegalStateException("ViewPager has no adapter set.");

        try {
            mViewPager = viewPager;

            mViewPager.addOnPageChangeListener(mOnPageChangeListener);

            addViews();
        } catch(Exception e) {
            Log.e(TAG, getMessageFor(e));
        }
    }

    /**
     * Add page indicators based on the attached ViewPager
     */
    private void addViews() {
        try {
            if ( mViewPager == null || mViewPager.getAdapter() == null || mViewPager.getAdapter().getCount() == 0 ) return;
            removeAllViews();
            AppCompatRadioButton firstItem = new AppCompatRadioButton(getContext());
            firstItem.setText("");
            firstItem.setButtonDrawable(mButtonDrawable.getConstantState().newDrawable());
            ViewPagerIndicator.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            firstItem.setLayoutParams(params);
            firstItem.setClickable(false);
            addView(firstItem);
            for ( int i=1; i<mViewPager.getAdapter().getCount(); i++ ) {
                AppCompatRadioButton item = new AppCompatRadioButton(getContext());
                item.setText("");
                item.setButtonDrawable(mButtonDrawable.getConstantState().newDrawable());
                params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(mItemDividerWidth, 0, 0, 0);
                item.setLayoutParams(params);
                item.setClickable(false);
                addView(item);
            }
            check(firstItem.getId());
        } catch(Exception e) {
            Log.e(TAG, getMessageFor(e));
        }
    }


    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            try {
                ViewPagerIndicator.this.check(ViewPagerIndicator.this.getChildAt(position).getId());
            } catch(Exception e) {
                Log.e(TAG, getMessageFor(e));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    /**
     * Always get a message for an exception
     * @param e an Exception
     * @return a String describing the Exception
     */
    private String getMessageFor(Exception e) {
        if ( e == null ) return TAG + ": No Message.";

        return e != null && e.getMessage() != null ? e.getMessage() : e.getClass().getName() + ": No Message.";
    }
}
