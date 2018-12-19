package com.xiaohou.screenshotwatermark;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Distant Place
 * Data : 2018/11/19 17:29
 * E-Mail: 605322850@qq.com
 * Desc :
 */
public class ImgUtlis {
    /**
     * 图片保存
     *
     * @param bmp
     * @return
     */
    public static void saveImageToGallery(Bitmap bmp, Context mContext,SaveImgCallBack callBack) {
        if (bmp == null) {
            if (callBack!=null){
                callBack.erro();
            }
            return;
        }
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
        String fileName = System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            makeRootDirectory(galleryPath);
        }
        File file = new File(galleryPath, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                if (callBack!=null) {
                    callBack.success(file.getPath());
                }
            } else {
                if (callBack!=null){
                    callBack.erro();
                }
            }
        } catch (IOException e) {
            if (callBack!=null){
                callBack.erro();
            }
            e.printStackTrace();
        }
    }

    /**
     * Android 5.0以下某些手机需要手动创建文件夹
     * @param filePath
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

        public interface SaveImgCallBack{
        void erro();
        void success(String path);
    }
}
