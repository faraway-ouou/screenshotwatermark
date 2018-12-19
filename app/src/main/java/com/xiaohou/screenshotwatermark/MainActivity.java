package com.xiaohou.screenshotwatermark;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xiaohou.basetools.base.BaseActivity;
import com.xiaohou.basetools.callback.IPermissionsCallBack;
import com.xiaohou.screenshot.IScreenshotCallBack;
import com.xiaohou.screenshot.ScreenshotBean;
import com.xiaohou.screenshot.ScreenshotManager;
import com.xiaohou.screenshot.WatermarkPostionCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.screenshot_rg_01)
    RadioGroup screenshotRg01;
    @BindView(R.id.screenshot_rg_02)
    RadioGroup screenshotRg02;
    private String[] prm = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private boolean type = true;
    private int position = 0;

    @Override
    protected void initData() {
        screenshotRg01.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.screenshot_rb_text:
                    type = false;
                    break;
                case R.id.screenshot_rb_bitmap:
                    type = true;
                    break;
            }
        });
        screenshotRg02.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.screenshot_rb_left_01:
                    position = WatermarkPostionCode.WATERMARK_POSITION_LEFT_TOP;
                    break;
                case R.id.screenshot_rb_right_01:
                    position = WatermarkPostionCode.WATERMARK_POSITION_RIGHT_TOP;
                    break;
                case R.id.screenshot_rb_right_02:
                    position = WatermarkPostionCode.WATERMARK_POSITION_RIGHT_BOTTOM;
                    break;
                case R.id.screenshot_rb_center:
                    position = WatermarkPostionCode.WATERMARK_POSITION_CENTER;
                    break;
                case R.id.screenshot_rb_left_02:
                    position = WatermarkPostionCode.WATERMARK_POSITION_LEFT_BOTTOM;
                    break;

            }
        });
        findViewById(R.id.screenshot_btn_01).setOnClickListener(v -> {
            requestPermissions(prm, true, false, new IPermissionsCallBack() {
                @Override
                public void permissionErro(String name) {

                }

                @Override
                public void permissionSuccess(String name) {
                    new ScreenshotManager.Builder(MainActivity.this)
                            .setWatermark(true)
                            .setImgWatermark(type)
                            .setwatermarkBitmap(ImageUtils.drawable2Bitmap(getResources().getDrawable(R.drawable.icon)))
                            .setText("这个是水印--------")
                            .setTextColor(Color.BLUE)
                            .setWatermarkPosition(position)
                            .setTextSize(18)
                            .addCallBack(new IScreenshotCallBack() {
                                @Override
                                public void onSuccess(ScreenshotBean bean) {
                                    ImgUtlis.saveImageToGallery(bean.getBitmap(), mContext, new ImgUtlis.SaveImgCallBack() {
                                        @Override
                                        public void erro() {
                                            ToastUtils.showShort("图片保存失败");
                                        }

                                        @Override
                                        public void success(String path) {
                                            ToastUtils.showShort("图片保存成功保存在：" + path);
                                        }
                                    });
                                }

                                @Override
                                public void onErro(ScreenshotBean bean) {
                                    ToastUtils.showShort("截图失败");
                                }
                            }).build().create();

                }
            });
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
