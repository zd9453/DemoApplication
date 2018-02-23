package com.example.zd.mycontentprovider.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.zd.mycontentprovider.R;

/**
 * use to do
 *
 * @author zhangdong on 2018/2/5 0005.
 * @version 1.0
 * @see .
 * @since 1.0
 */

public class ViewTest extends View {

    private static final String TAG = "ViewTest";
    private Paint mPaint;

    public ViewTest(Context context) {
        super(context);
        init();
    }

    public ViewTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * dp转px
     *
     * @param dp .
     * @return px值
     */
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    /**
     * 初始化画笔
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);//设置颜色
        mPaint.setAntiAlias(true);//开启抗锯齿
        mPaint.setStrokeWidth(5);//线条宽度5像素
        mPaint.setStyle(Paint.Style.STROKE);//画笔是否空心
        mPaint.setTypeface(Typeface.MONOSPACE);//设置绘制模式
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置笔夹形状
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();
        int min = Math.min(width, height);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_test);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int min1 = Math.min(bitmapWidth, bitmapHeight);

        int minR = Math.min(min, min1);

        Path path = new Path();
        path.addCircle(width / 2, height / 2, minR / 2, Path.Direction.CCW);

        path.setFillType(Path.FillType.EVEN_ODD);

        //路径裁剪（先裁剪画布再往上绘制）
//        canvas.clipPath(path);

        Log.d(TAG, "onDraw: -------- " + getWidth() + " " + getHeight());

        //画一层颜色
        canvas.drawColor(Color.parseColor("#cccccc"));

       /* if (min <= min1) {
            int scal = min / min1;
            bitmap.setDensity(scal);
        } else {
            bitmap.setDensity(min1 / min);
        }*/

        //画图
        canvas.drawBitmap(bitmap,
                Math.abs(getWidth() - bitmapWidth) / 2,
                Math.abs(getHeight() - bitmapHeight) / 2,
                mPaint);
        bitmap.recycle();

        //画圆
        canvas.drawCircle(width / 2, height / 2, min / 2 - 20, mPaint);

        canvas.drawRoundRect(
                width / 2 - dpToPx(20),
                height / 2 - dpToPx(20),
                width / 2 + dpToPx(20),
                height / 2 + dpToPx(20),
                10f, 10f, mPaint);

        mPaint.setStrokeWidth(dpToPx(5));
        mPaint.setStrokeCap(Paint.Cap.SQUARE);

        canvas.drawPoint(width / 2, height / 2, mPaint);

        if (isSelected()) {
            canvas.drawColor(Color.YELLOW);
        }

    }
}
