package graduation.chn.cn.graduation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import graduation.chn.cn.fragment.HomeFragment;
import graduation.chn.cn.fragment.MarketFragment;
import graduation.chn.cn.fragment.PersonFragment;
import graduation.chn.cn.fragment.StudyFragment;

public class MainActivity extends AppCompatActivity {


    private RadioButton rb_home;
    private RadioButton rb_study;
    private RadioButton rb_market;
    private RadioButton rb_person;
    private Fragment homefragment, studyfragment, marketfragment, personfragment;
    private RadioGroup rg_menu;
    private FragmentManager fragmanager;
//    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initactivity();
        initview();//加载控件
        loadfragment();

    }


    /*
    * 调用方法开始*/
    public void initview() {
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_study = (RadioButton) findViewById(R.id.rb_study);
        rb_market = (RadioButton) findViewById(R.id.rb_market);
        rb_person = (RadioButton) findViewById(R.id.rb_person);
        rg_menu = (RadioGroup) findViewById(R.id.rg_menu);
    }//获取相应控件id

    void initactivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }//启动另一个activity

    public void loadfragment() {
        fragmanager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();//开启事务对象
        rb_home.setChecked(true);
        if (homefragment == null) {
            homefragment = new HomeFragment();
            fragmentTransaction.add(R.id.fl_content, homefragment);//填充内容
        } else {
            fragmentTransaction.show(homefragment);
        }
        fragmentTransaction.commit();
        rg_menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();//开启事务对象
                hidefragment(fragmentTransaction);
                switch (checkedId) {
                    case R.id.rb_home:
                        if (homefragment == null) {
                            homefragment = new HomeFragment();
                            fragmentTransaction.add(R.id.fl_content, homefragment);//填充内容
                        } else {
                            fragmentTransaction.show(homefragment);
                        }
                        break;
                    case R.id.rb_study:
                        if (studyfragment == null) {
                            studyfragment = new StudyFragment();
                            fragmentTransaction.add(R.id.fl_content, studyfragment);//填充内容
                        } else {
                            fragmentTransaction.show(studyfragment);

                        }
                        break;
                    case R.id.rb_market:
                        if (marketfragment == null) {
                            marketfragment = new MarketFragment();
                            fragmentTransaction.add(R.id.fl_content, marketfragment);//填充内容
                        } else {
                            fragmentTransaction.show(marketfragment);

                        }
                        break;
                    case R.id.rb_person:
                        if (personfragment == null) {
                            personfragment = new PersonFragment();
                            fragmentTransaction.add(R.id.fl_content, personfragment);//填充内容
                        } else {
                            fragmentTransaction.show(personfragment);
                        }
                        break;
                }//switch结束
                fragmentTransaction.commit();
            }
        });

    }//加载fragment

    public void hidefragment(FragmentTransaction fragmentTransaction) {
        if (homefragment != null) fragmentTransaction.hide(homefragment);
        if (studyfragment != null) fragmentTransaction.hide(studyfragment);
        if (marketfragment != null) fragmentTransaction.hide(marketfragment);
        if (personfragment != null) fragmentTransaction.hide(personfragment);
    }//隐藏fragment
}
