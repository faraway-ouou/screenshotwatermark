package com.xiaohou.screenshot;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by Distant Place
 * Data : 2018/12/18 10:32
 * E-Mail: 605322850@qq.com
 * Desc :  截图
 */
public class ScreenshotManager {
    private Activity activity;
    private IScreenshotCallBack callBack;
    private boolean addWatermark;//是否添加水印
    private boolean addImgWatermark;//是否添加图片水印
    private int watermarkPosition;//添加水印位置
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private String watermarkText;//水印文字
    private int watermarkTextColor;
    private int watermarkTextSize;
    private Bitmap watermarkBitmap;


    public ScreenshotManager(Builder builder) {
        this.activity = builder.activity;
        this.callBack = builder.callBack;
        this.addWatermark = builder.addWatermark;
        this.addImgWatermark = builder.addImgWatermark;
        this.watermarkPosition = builder.watermarkPosition;
        this.paddingTop = builder.paddingTop;
        this.paddingBottom = builder.paddingBottom;
        this.paddingLeft = builder.paddingLeft;
        this.paddingRight = builder.paddingRight;
        this.watermarkText = builder.watermarkText;
        this.watermarkTextColor = builder.watermarkTextColor;
        this.watermarkTextSize = builder.watermarkTextSize;
        this.watermarkBitmap = builder.watermarkBitmap;

    }

    public static class Builder {

        private final Activity activity;
        private IScreenshotCallBack callBack;
        private boolean addWatermark = false;
        private boolean addImgWatermark = false;
        private int watermarkPosition = WatermarkPostionCode.WATERMARK_POSITION_CENTER;
        private Bitmap watermarkBitmap;
        private int paddingTop = 0;
        private int paddingBottom = 0;
        private int paddingLeft = 0;
        private int paddingRight = 0;
        private String watermarkText = "";
        private int watermarkTextColor = Color.BLACK;
        private int watermarkTextSize = 12;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder addCallBack(IScreenshotCallBack callBack) {
            this.callBack = callBack;
            return this;
        }
        public Builder setWatermark(boolean addWatermark) {
            this.addWatermark = addWatermark;
            return this;
        }

        public Builder setTextColor(int watermarkTextColor) {
            this.watermarkTextColor = watermarkTextColor;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.watermarkTextSize = watermarkTextSize;
            return this;
        }

        public Builder setImgWatermark(boolean addImgWatermark) {
            this.addImgWatermark = addImgWatermark;
            return this;
        }

        public Builder setWatermarkPosition(int watermarkPosition) {
            this.watermarkPosition = watermarkPosition;
            return this;
        }

        public Builder setwatermarkBitmap(Bitmap watermarkBitmap) {
            this.watermarkBitmap = watermarkBitmap;
            return this;
        }

        public Builder setText(String watermarkText) {
            this.watermarkText = watermarkText;
            return this;
        }

        public Builder setPaddingTop(int paddingTop) {
            this.paddingTop = paddingTop;
            return this;
        }

        public Builder setPaddingBottom(int paddingBottom) {
            this.paddingBottom = paddingBottom;
            return this;
        }

        public Builder setPaddingLeft(int paddingLeft) {
            this.paddingLeft = paddingLeft;
            return this;
        }

        public Builder setPaddingRight(int paddingRight) {
            this.paddingRight = paddingRight;
            return this;
        }

        public ScreenshotManager build() {
            return new ScreenshotManager(this);
        }

    }

    /**
     * 创建截图
     */
    public void create() {
        Bitmap newBitmap = null;
        Bitmap bitmap = capture(activity);
        if (bitmap != null) {
            newBitmap = bitmap;
            if (addWatermark) {
                if (addImgWatermark) {
                    if (watermarkPosition == WatermarkPostionCode.WATERMARK_POSITION_CENTER) {
                        newBitmap = WatermarkBitmapUtil.createWaterMaskCenter(capture(activity), watermarkBitmap);
                    } else if (watermarkPosition == WatermarkPostionCode.WATERMARK_POSITION_LEFT_TOP) {
                        newBitmap = WatermarkBitmapUtil.createWaterMaskLeftTop(activity, capture(activity), watermarkBitmap, paddingLeft, paddingTop);
                    } else if (watermarkPosition == WatermarkPostionCode.WATERMARK_POSITION_RIGHT_TOP) {
                        newBitmap = WatermarkBitmapUtil.createWaterMaskRightTop(activity, capture(activity), watermarkBitmap, paddingRight, paddingTop);
                    } else if (watermarkPosition == WatermarkPostionCode.WATERMARK_POSITION_LEFT_BOTTOM) {
                        newBitmap = WatermarkBitmapUtil.createWaterMaskLeftBottom(activity, capture(activity), watermarkBitmap, paddingRight, paddingBottom);
                    } else {
                        newBitmap = WatermarkBitmapUtil.createWaterMaskRightBottom(activity, capture(activity), watermarkBitmap, paddingLeft, paddingBottom);
                    }
                } else {
                    if (watermarkPosition == WatermarkPostionCode.WATERMARK_POSITION_CENTER) {
                        newBitmap = WatermarkBitmapUtil.drawTextToCenter(activity.getApplicationContext(), capture(activity), watermarkText, watermarkTextSize, watermarkTextColor);
                    } else if (watermarkPosition == WatermarkPostionCode.WATERMARK_POSITION_LEFT_TOP) {
                        newBitmap = WatermarkBitmapUtil.drawTextToLeftTop(activity, capture(activity), watermarkText, watermarkTextSize, watermarkTextColor, paddingLeft, paddingTop);
                    } else if (watermarkPosition == WatermarkPostionCode.WATERMARK_POSITION_RIGHT_TOP) {
                        newBitmap = WatermarkBitmapUtil.drawTextToRightTop(activity, capture(activity), watermarkText, watermarkTextSize, watermarkTextColor, paddingRight, paddingTop);
                    } else if (watermarkPosition == WatermarkPostionCode.WATERMARK_POSITION_LEFT_BOTTOM) {
                        newBitmap = WatermarkBitmapUtil.drawTextToRightBottom(activity, capture(activity), watermarkText, watermarkTextSize, watermarkTextColor, paddingRight, paddingBottom);
                    } else {
                        newBitmap = WatermarkBitmapUtil.drawTextToLeftBottom(activity, capture(activity), watermarkText, watermarkTextSize, watermarkTextColor, paddingLeft, paddingBottom);
                    }
                }
            }
        }
        if (newBitmap!=null){
            callBack.onSuccess(new ScreenshotBean(WatermarkPostionCode.WATERMARK_SUCCESS,newBitmap,true));
        }else {
            callBack.onErro(new ScreenshotBean(WatermarkPostionCode.WATERMARK_ERRO,newBitmap,false));

        }
    }

    /**
     * 截图
     *
     * @param activity
     * @return
     */
    public static Bitmap capture(Activity activity) {
        View view = activity.getWindow().getDecorView();
        //允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        WindowManager windowManager = activity.getWindowManager();
        //获取屏幕宽和高
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        //获取标题栏高度
        Rect outRect1 = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        //View绘制区域
        Rect outRect2 = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect2);
        int titleHeight = outRect1.height() - outRect2.height();
        Bitmap newBitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, (statusBarHeight + titleHeight), width, height - (statusBarHeight + titleHeight));
        //销毁缓存信息
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);
        return newBitmap;
    }
}
