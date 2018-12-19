package com.xiaohou.screenshot;

/**
 * Created by Distant Place
 * Data : 2018/12/18 10:38
 * E-Mail: 605322850@qq.com
 * Desc : 回调
 */
public interface IScreenshotCallBack {
    void onSuccess(ScreenshotBean bean);
    void onErro(ScreenshotBean bean);
}
