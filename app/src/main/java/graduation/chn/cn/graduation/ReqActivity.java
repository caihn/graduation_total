package graduation.chn.cn.graduation;

import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import graduation.chn.cn.entity.CheckNetState;
import graduation.chn.cn.entity.MyhttpClientHelper;

public class ReqActivity extends AppCompatActivity {

    private Button bt_reg;
    private EditText et_user, et_pwd, et_email;
    private TextView tv_notice_reg, tv_include_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req);
        initview();
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = et_user.getText().toString();
                String pwd = et_pwd.getText().toString();
                final String email = et_email.getText().toString();
                if (username.equals("") || pwd.equals("") || email.equals("")) {
                    Toast.makeText(ReqActivity.this, "输入信息不完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!email.contains("@"))
                {
                    Toast.makeText(ReqActivity.this, "邮箱地址不合法！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String net = CheckNetState.checkState(ReqActivity.this);
                if (net == null) {
                    return;
                }
                bt_reg.setText("正在注册。。。");
                String url = "admin/api/register.html";
                RequestParams params = new RequestParams();
                params.put("username", username);
                params.put("pwd", pwd);
                params.put("email", email);
                MyhttpClientHelper.post(ReqActivity.this, url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        Toast.makeText(ReqActivity.this, "系统繁忙，请重试！", Toast.LENGTH_SHORT).show();
                        bt_reg.setText("注册");
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        s = s.trim();
                        if (s.equals("exist")) {
                            Toast.makeText(ReqActivity.this, "用户已存在！", Toast.LENGTH_SHORT).show();
                            bt_reg.setText("注册");
                            return;
                        } else if (s.equals("email")) {
                            Toast.makeText(ReqActivity.this, "邮箱已被使用", Toast.LENGTH_SHORT).show();
                            bt_reg.setText("注册");
                        } else if(Integer.valueOf(s)>=1) {
                            String url="admin/api/sendreg/"+username+".html";
                            MyhttpClientHelper.get(ReqActivity.this, url, new TextHttpResponseHandler() {
                                @Override
                                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                    Toast.makeText(ReqActivity.this, "系统繁忙！", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onSuccess(int i, Header[] headers, String s) {
                                    s=s.trim();
                                    if(s.equals("ok"))
                                    {
                                        Toast.makeText(ReqActivity.this, "注册成功，已向邮箱"+email+"发送激活信息，请注意查收", Toast.LENGTH_LONG).show();
                                        Intent intent=new Intent(ReqActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                        return;
                                    }
                                    else if(s.equals("failed"))
                                    {
                                        Toast.makeText(ReqActivity.this, "注册成功，向邮箱"+email+"发送激活信息失败，请联系管理员", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                            });
                            bt_reg.setText("注册");

                        }
                    }
                });
            }
        });//注册按钮点击事件
        tv_notice_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReqActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });//跳转登陆按钮点击事件
    }


    /*
    * 调用方法
    * */
//    加载控件
    void initview() {
        bt_reg = (Button) findViewById(R.id.bt_reg);
        et_user = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_email = (EditText) findViewById(R.id.et_email);
        tv_notice_reg = (TextView) findViewById(R.id.tv_notice_reg);
        tv_include_title = (TextView) findViewById(R.id.tv_include_title);
        tv_include_title.setText("欢迎注册");

    }
}
