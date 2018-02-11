package com.example.zd.mycontentprovider.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.example.zd.mycontentprovider.R;

/**
 * use to do 如果限定textView的宽度，则根据宽度显示文字,字体变小
 *
 * @author zhangdong on 2018/2/11 0011.
 * @version 1.0
 * @see .
 * @since 1.0
 */

public class WrapContentTextView extends AppCompatTextView {

    private static final String TAG = "WrapContentTextView";
    private Paint mPaint;
    private Boolean isWrapContent = false;//是否开启

    public void setWrapContent(Boolean wrapContent) {
        isWrapContent = wrapContent;
        invalidate();
    }

    public Boolean getWrapContent() {
        return isWrapContent;
    }

    public WrapContentTextView(Context context) {
        super(context);
        init();
    }

    public WrapContentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WrapContentTextView, 0, 0);
        isWrapContent = typedArray.getBoolean(R.styleable.WrapContentTextView_isWrapContent, false);
        init();
    }

    public WrapContentTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (isWrapContent) {
            CharSequence text = getText();
            int width = getWidth();
            int height = getHeight();
            Log.d(TAG, "onDraw: --------- width = " + width + "; height = " + height);

            float density = getResources().getDisplayMetrics().density;
            Log.d(TAG, "onDraw: ------- density = " + density);

            if (!TextUtils.isEmpty(text)) {
                //文字的字数
                int length = text.length();

                //view的内边距
                int paddingLeft = getPaddingLeft();
                int paddingRight = getPaddingRight();
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();

                Log.d(TAG, "onDraw: --- paddingLeft = " + paddingLeft +
                        "; paddingTop = " + paddingTop +
                        "; paddingRight = " + paddingRight +
                        "; paddingBottom = " + paddingBottom);

                //文字可绘制的宽高
                int textWidth = width - paddingLeft - paddingRight;
                int textHeight = height - paddingTop - paddingBottom;

                //一个字可绘制的宽度
                int oneWidth = textWidth / length;

                //一个字可绘制的宽度与可绘制的高度取最小的值
                int textSize = Math.min(oneWidth, textHeight);

                Log.d(TAG, "onDraw: ------ 文字长度：" + length +
                        "; textWidth = " + textWidth +
                        "; oneWidth = " + oneWidth +
                        "; textHeight = " + textHeight);

                //当前文字的size
                float size = getTextSize();
                float measureText = getPaint().measureText(text, 0, length);
                Log.d(TAG, "onDraw: ------- size = " + size + "; measureText = " + measureText);

                //获取当前文章的高度
                Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
                float tHeight = fontMetrics.bottom - fontMetrics.top;

                Log.d(TAG, "onDraw: --------- fontMetrics.bottom= " + fontMetrics.bottom
                        + "; fontMetrics.top= " + fontMetrics.top);

                float newSize = 0;
                //新的文字大小根据宽度比例关系得到
                float newSizeW = size * textSize * length / measureText;
                //新的文字大小根据高度比例关系得到
                float newSizeH = size * textHeight / tHeight;
                Log.d(TAG, "onDraw: ------ newSizeW= " + newSizeW + "; newSizeH= " + newSizeH);

                newSize = Math.min(newSizeW, newSizeH);

                Log.d(TAG, "onDraw:  ------- newSize : " + newSize);

                mPaint.setTextSize(newSize);
                mPaint.setColor(getCurrentTextColor());

                Rect textRect = new Rect();
                mPaint.getTextBounds(text.toString(), 0, length, textRect);

                int startX = (width - textRect.width() - paddingLeft - paddingRight) / 2;
                int startY = height - paddingBottom - (textHeight - textRect.height()) / 2;

                Log.d(TAG, "onDraw: ---- textRect.width()= " + textRect.width() +
                        "; textRect.height()= " + textRect.height());

                canvas.drawText(text, 0, length, startX, startY, mPaint);

            }
        } else super.onDraw(canvas);
    }
}
