package com.bawei.daaa09lianx.net;


import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Author:杨帅
 * Date:2019/6/17 9:34
 * Description：网络请求接口
 */
public interface NetCallBack {
    void onSuccess(Object object);
    void onFailure(String msg);
}
