package demo.com.takeout.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import demo.com.takeout.R;
import demo.com.takeout.ui.activity.LoginActivity;

/**
 * Created by HASEE on 2017/1/9.
 */
public class UserFragment extends BaseFragment {
    @InjectView(R.id.tv_user_setting)
    ImageView tvUserSetting;
    @InjectView(R.id.iv_user_notice)
    ImageView ivUserNotice;
    @InjectView(R.id.login)
    ImageView login;
    @InjectView(R.id.username)
    TextView username;
    @InjectView(R.id.phone)
    TextView phone;
    @InjectView(R.id.ll_userinfo)
    LinearLayout llUserinfo;
    @InjectView(R.id.iv_address)
    ImageView ivAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_user, null);
        ButterKnife.inject(this, view);
        return view;
    }
    @OnClick({R.id.login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
