package com.example.zd.demobase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zd.demobase.R;


/**
 * 定义一个简单的顶部组合布局，类似页面调用，根据应用场景做相应功能的增加
 * Created by zhangdong on 2017/11/21 0021.
 */

public class SimpleToolbar extends RelativeLayout {

    private String leftText, rightText, titleText;
    private Drawable leftIcon, rightIcon;
    private int leftTextColor, rightTextColor, titleTextColor;
    //    private float leftSize, rightSize, titleSize;
    private TextView tvLeft, tvTitle, tvRight;
    private ImageView imgLeft, imgRight;
    private LeftClickListener leftClickListener;
    private RightClickListener rightClickListener;

    /**
     * 直接New走此方法
     */
    public SimpleToolbar(Context context) {
        super(context);
        initView(context);
        initEvent();
    }

    /**
     * XML中使用走此方法
     */
    public SimpleToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleToolbar, 0, 0);
        leftText = typedArray.getString(R.styleable.SimpleToolbar_leftText);
        rightText = typedArray.getString(R.styleable.SimpleToolbar_rightText);
        titleText = typedArray.getString(R.styleable.SimpleToolbar_titleText);
        leftIcon = typedArray.getDrawable(R.styleable.SimpleToolbar_leftIcon);
        rightIcon = typedArray.getDrawable(R.styleable.SimpleToolbar_rightIcon);
        leftTextColor = typedArray.getColor(R.styleable.SimpleToolbar_leftColor, Color.BLACK);
        rightTextColor = typedArray.getColor(R.styleable.SimpleToolbar_rightColor, Color.BLACK);
        titleTextColor = typedArray.getColor(R.styleable.SimpleToolbar_titleColor, Color.BLACK);
//        leftSize = typedArray.getDimension(R.styleable.SimpleToolbar_leftSize, 12f);
//        rightSize = typedArray.getDimension(R.styleable.SimpleToolbar_rightSize, 12f);
//        titleSize = typedArray.getDimension(R.styleable.SimpleToolbar_titleSize, 12f);
        typedArray.recycle();
        initView(context);
        initEvent();
    }

    public SimpleToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initEvent();
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_simple_toolbar, this, true);
        tvLeft = ((TextView) inflate.findViewById(R.id.tv_left));
        tvTitle = ((TextView) inflate.findViewById(R.id.tv_title));
        tvRight = ((TextView) inflate.findViewById(R.id.tv_right));
        imgLeft = ((ImageView) inflate.findViewById(R.id.img_left));
        imgRight = ((ImageView) inflate.findViewById(R.id.img_right));

        if (null != leftText) {
            tvLeft.setText(leftText);
//            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, leftSize);
//            tvLeft.setTextSize(leftSize);
            tvLeft.setTextColor(leftTextColor);
            tvLeft.setVisibility(VISIBLE);
        }

        if (null != rightText) {
            tvRight.setText(rightText);
//            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, rightSize);
//            tvRight.setTextSize(rightSize);
            tvRight.setTextColor(rightTextColor);
            tvRight.setVisibility(VISIBLE);
        }

        if (null != titleText) {
            tvTitle.setText(titleText);
//            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
//            tvTitle.setTextSize(titleSize);
            tvTitle.setTextColor(titleTextColor);
            tvTitle.setVisibility(VISIBLE);
        }

        if (null != leftIcon) {
            imgLeft.setImageDrawable(leftIcon);
            imgLeft.setVisibility(VISIBLE);
        }

        if (null != rightIcon) {
            imgRight.setImageDrawable(rightIcon);
            imgRight.setVisibility(VISIBLE);
        }
    }

    private void initEvent() {
        imgLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftClickListener != null) {
                    leftClickListener.leftClick();
                }
            }
        });
        imgRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightClickListener != null) {
                    rightClickListener.rightClick();
                }
            }
        });
    }

    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getWidth(), getHeight());
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getWidth(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, getHeight());
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }*/

    public void setLeftClickListener(LeftClickListener leftClickListener) {
        this.leftClickListener = leftClickListener;
    }

    public void setRightClickListener(RightClickListener rightClickListener) {
        this.rightClickListener = rightClickListener;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
        tvLeft.setText(leftText);
        tvLeft.setVisibility(VISIBLE);
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
        tvRight.setText(rightText);
        tvRight.setVisibility(VISIBLE);
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
        tvTitle.setText(titleText);
        tvTitle.setVisibility(VISIBLE);
    }

    public void setLeftIcon(Drawable leftIcon) {
        this.leftIcon = leftIcon;
        imgLeft.setImageDrawable(leftIcon);
        imgLeft.setVisibility(VISIBLE);
    }

    public void setRightIcon(Drawable rightIcon) {
        this.rightIcon = rightIcon;
        imgRight.setImageDrawable(rightIcon);
        imgRight.setVisibility(VISIBLE);
    }

    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
        tvLeft.setTextColor(leftTextColor);
    }

    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
        tvRight.setTextColor(rightTextColor);
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        tvTitle.setTextColor(titleTextColor);
    }

    /**
     * 左边点击
     */
    public interface LeftClickListener {
        void leftClick();
    }

    /**
     * 右边点击
     */
    public interface RightClickListener {
        void rightClick();
    }
}
