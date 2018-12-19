package com.xiaohou.screenshot;

import android.graphics.Bitmap;

/**
 * Created by Distant Place
 * Data : 2018/12/18 10:39
 * E-Mail: 605322850@qq.com
 * Desc :
 */
public class ScreenshotBean {
    int code;
    Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    boolean isSuccess;

    @Override
    public String toString() {
        return "ScreenshotBean{" +
                "code=" + code +
                ", bitmap=" + bitmap +
                ", isSuccess=" + isSuccess +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ScreenshotBean(int code, Bitmap bitmap, boolean isSuccess) {
        this.code = code;
        this.bitmap = bitmap;
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;

    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
