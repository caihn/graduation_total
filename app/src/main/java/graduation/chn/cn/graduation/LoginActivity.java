package graduation.chn.cn.graduation;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {
    private Button bt_login;
    EditText et_user, et_pwd;
    TextView tv_notice_login;
    private TextView tv_include_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = et_user.getText().toString();
                String pwd = et_pwd.getText().toString();
                if(username.equals("")||pwd.equals(""))
                {
                    Toast.makeText(LoginActivity.this,"请输入用户名和密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                String state=CheckNetState.checkState(LoginActivity.this);
                if(state==null)
                {
                    return;
                }
                bt_login.setText("正在登陆。。。");
                String url = "admin/api/login/" + username + "/" + pwd + ".html";
                RequestParams params=new RequestParams();
                params.put("username",username);
                params.put("pwd",pwd);
                MyhttpClientHelper.post(LoginActivity.this, url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, org.apache.http.Header[] headers, String s, Throwable throwable) {
                        Toast.makeText(LoginActivity.this, "系统繁忙，请重试", Toast.LENGTH_SHORT).show();
                        bt_login.setText("登陆");

                    }

                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, String s) {
                        if (s.equals("404")) {
                            Toast.makeText(LoginActivity.this,"用户名或密码错误，请重试！",Toast.LENGTH_SHORT).show();
                        }
                        else if(s.equals("1"))
                        {
                            Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("您的账号尚未激活，请查收邮箱激活账号，或点击发送重新获取激活邮件");
                            builder.setTitle("提示！");
                            builder.setIcon(R.mipmap.notice);
                            builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url="admin/api/sendreg/"+username+".html";
                                    MyhttpClientHelper.get(LoginActivity.this, url, new TextHttpResponseHandler() {
                                        @Override
                                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                            Toast.makeText(LoginActivity.this,"系统繁忙，请稍后重试",Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onSuccess(int i, Header[] headers, String s) {

                                           if(s.trim().equals("ok"))
                                            {
                                                Toast.makeText(LoginActivity.this,"发送成功，请注意查收!",Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            else if(s.trim().equals("failed"))
                                            {
                                                Toast.makeText(LoginActivity.this,"发送失败，请重试!",Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();

                                            }
                                            return;
                                        }
                                    });
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.create();
                            builder.show();
                        }
                        bt_login.setText("登陆");
                    }
                });

            }
        });//登陆按钮点击事件

        tv_notice_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ReqActivity.class);
                startActivity(intent);
                finish();
            }
        });//切换至注册点击事件
    }

    /*调用方法
    * */

    //    加载id
    void initview() {
        bt_login = (Button) findViewById(R.id.bt_login);
        et_user = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        tv_notice_login = (TextView) findViewById(R.id.tv_notice_login);
        tv_include_title = (TextView) findViewById(R.id.tv_include_title);
        tv_include_title.setText("欢迎登陆");
    }

}
