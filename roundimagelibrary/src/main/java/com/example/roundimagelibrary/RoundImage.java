package com.example.roundimagelibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * author:Huanglinqing
 * data:{2019/9/13}
 * blog:https://blog.csdn.net/huangliniqng/
 *
 * @desc: 自定义圆形头像
 **/
@SuppressLint("AppCompatCustomView")
public class RoundImage extends ImageView {

    private static final String TAG = "RoundImage";

    /**
     * 设置的图片资源
     */
    private Drawable drawable;

    /**
     * 设置的资源转换后的bitmap
     */
    private Bitmap bitmap;

    /**
     * 裁剪后的bitmpa
     */
    private Bitmap circleBitmap;


    public RoundImage(Context context) {
//        this(context, null);

        this(context,null);
        Log.d(TAG, "一个参数的构造方法");
    }

    public RoundImage(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        Log.d(TAG, "两个参数的构造方法");
        drawable = this.getDrawable();
        init(context);

    }

    public RoundImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /***
     * 初始化属性
     */
    private void init(Context context) {
        bitmap = getBitmapFromDrawable(drawable);
        setImageBitmap(getCircleBitmap(bitmap));
    }

    /**
     * 获取圆形裁剪的bitmap
     *
     * @param bitmap 原bitmap
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);

        float roundRa = 0.0f;
        if (bitmap.getWidth() > bitmap.getHeight()) {
            roundRa = bitmap.getHeight() / 2.0f;
        } else {
            roundRa = bitmap.getWidth() / 2.0f;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.GRAY);
        canvas.drawRoundRect(rectF, roundRa, roundRa, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return circleBitmap;

    }

    /**
     * 根据drawable 获取bitmap
     *
     * @param drawable
     */
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            throw new Resources.NotFoundException("Image resource not set");
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas();
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }
    }
}
