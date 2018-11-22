package com.example.ruiyonghui.zhongkezhihe.ui.base;

public class BasePresenter<T extends BaseContract.BaseView>implements BaseContract.BasePresenter<T> {
    protected T mView;
    @Override
    public void attchView(T view) {
        this.mView=view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
