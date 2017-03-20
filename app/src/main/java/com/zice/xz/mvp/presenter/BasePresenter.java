package com.zice.xz.mvp.presenter;

import java.lang.ref.WeakReference;

/**
 * Copyright (c) 2017,xxxxxx All rights reserved.
 * author：Z.kai
 * date：2017/3/20
 * description：
 */

public abstract class BasePresenter<T> {
    public final String TAG = getClass().getName();
    public WeakReference<T> viewWF;

    /**
     * 自动关联view和presenter
     * @param t 关联对象
     */
    public BasePresenter(T t){
        attach(t);
    }
    /**
     * 设置view和presenter关联
     * @param t 关联对象
     */
    private void attach(T t){
        viewWF = new WeakReference<T>(t);
    }

    /**
     * 检查是否关联
     * @return 是否关联
     */
    public boolean isAttach(){
        return viewWF != null && viewWF.get() != null;
    }

    /**
     * 获取关联对象
     * @return
     */
    public T getView(){
        if (isAttach()) {
            return viewWF.get();
        }
        return null;
    }
    
    public void detach(){
        if (viewWF != null) {
            viewWF.clear();
            viewWF = null;
        }
    }
    
}
