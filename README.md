## 功能介绍
 截取当前Activity添加水印
 
 ##	使用指南

 1.	导入依赖
  Gradle
```gradle
	dependencies {
	        implementation 'com.github.distantplace-z:screenshotwatermark:v1.0'
	}
```
项目Gradle：
```gradle
allprojects {
  repositories {
    	maven { url 'https://jitpack.io' }
    }
}
```

2.	使用方法
```java
  ScreenshotManager.Builder builder =  new ScreenshotManager.Builder(MainActivity.this);
                    //是否开启水印
                    builder.setWatermark(true);
                    //是否开启图片水印反之则是文字水印
                    builder.setImgWatermark(true);
                    //添加水印图片
                    builder.setwatermarkBitmap(ImageUtils.drawable2Bitmap(getResources().getDrawable(R.drawable.icon)));
                    //添加文字水印
                    builder.setText("这个是水印--------");
                    //文字水印颜色
                    builder.setTextColor(Color.BLUE);
                    //水印位置
//                    WatermarkPostionCode.WATERMARK_POSITION_LEFT_TOP
//                    WatermarkPostionCode.WATERMARK_POSITION_RIGHT_TOP
//                    WatermarkPostionCode.WATERMARK_POSITION_RIGHT_BOTTOM
//                    WatermarkPostionCode.WATERMARK_POSITION_CENTER
//                    WatermarkPostionCode.WATERMARK_POSITION_LEFT_BOTTOM
                    builder.setWatermarkPosition(WatermarkPostionCode.WATERMARK_POSITION_LEFT_TOP);
                    //文字水印字体大小
                    builder.setTextSize(18);
                    //水印边距
                    builder.setPaddingBottom(100)
                            .setPaddingLeft(100)
                            .setPaddingRight(100)
                            .setPaddingTop(100);
                    //水印图片回调
                    builder.addCallBack(new IScreenshotCallBack() {
                                //成功
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
                                //失败
                                @Override
                                public void onErro(ScreenshotBean bean) {
                                    ToastUtils.showShort("截图失败");
                                }
                            });
                    //提交创建
                    builder.build().create();
```
### 效果图
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181219185021410.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzMzNzIyOTMw,size_16,color_FFFFFF,t_70)
