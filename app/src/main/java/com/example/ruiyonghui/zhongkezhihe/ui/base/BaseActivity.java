package com.example.ruiyonghui.zhongkezhihe.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.example.ruiyonghui.zhongkezhihe.inter.IBase;
import com.example.ruiyonghui.zhongkezhihe.untils.SharedPreferencesUtils;

import javax.inject.Inject;

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends FragmentActivity implements IBase,BaseContract.BaseView {

    @Inject
    protected T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        inject();
        //绑定
        if (mPresenter != null) {
            mPresenter.attchView(this);
        }
    }


    @Override
    public void initView(View view) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
    protected String getUid() {
        return (String) SharedPreferencesUtils.getParam(this, "uid", "");
    }

    protected String getToken() {
        return (String) SharedPreferencesUtils.getParam(this, "token", "");
    }

    protected void toast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Context context, CharSequence message) {
        Toast mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(message);
        mToast.show();
    }
}
