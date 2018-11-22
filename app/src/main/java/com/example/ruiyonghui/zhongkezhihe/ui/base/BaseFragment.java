package com.example.ruiyonghui.zhongkezhihe.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.ruiyonghui.zhongkezhihe.inter.IBase;
import com.example.ruiyonghui.zhongkezhihe.untils.SharedPreferencesUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends Fragment implements IBase,BaseContract.BaseView
{
    private ProgressDialog mProgressDialog;
    @Inject
    protected T mPresenter;
    private Unbinder bind;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        if (mPresenter != null) {
            mPresenter.attchView(this);
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (bind != null) {
            bind.unbind();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), null);
        bind = ButterKnife.bind(getActivity(), view);
        initView(view);
        return view;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    protected String getUid() {
        return (String) SharedPreferencesUtils.getParam(getContext(), "uid", "");
    }

    protected String getToken() {
        return (String) SharedPreferencesUtils.getParam(getContext(), "token", "");
    }
    public static void showShort(Context context, CharSequence message) {
        Toast mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(message);
        mToast.show();
    }
    public abstract Fragment newInstance(
    );
}
