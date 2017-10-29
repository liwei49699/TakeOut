package demo.com.takeout.ui.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import demo.com.takeout.*;

/**
 * Created by HASEE on 2017/1/10.
 */
public class LoginActivity extends BaseActivity {

    @InjectView(R.id.iv_user_back)
    ImageView ivUserBack;
    @InjectView(R.id.iv_user_password_login)
    TextView ivUserPasswordLogin;
    @InjectView(R.id.et_user_phone)
    EditText etUserPhone;
    @InjectView(R.id.tv_user_code)
    TextView tvUserCode;
    @InjectView(R.id.login)
    TextView login;
    private EventHandler eventHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        tvUserCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LoginActivity.this, "发送验证码", Toast.LENGTH_SHORT).show();
                sendCode();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitCode();
            }
        });

        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, final int result, final Object data) {

                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(LoginActivity.this, data + "收取" + result, Toast.LENGTH_SHORT).show();
                            }
                        });
                        // 处理你自己的逻辑
                    } else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(LoginActivity.this, data + "提交" + result, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        };

        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
        SMSSDK.setAskPermisionOnReadContact(true);
        SMSSDK.registerEventHandler(eventHandler);
    }

    private void submitCode() {

        String code = etUserPhone.getText().toString();

        SMSSDK.submitVerificationCode("+86", "18813197864", code);
    }

    private void sendCode() {

        SMSSDK.getVerificationCode("86", "18813197864");

    }
}
