package graduation.chn.cn.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import graduation.chn.cn.graduation.R;

/**
 * Created by len on 2016/11/29.
 */

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_home,null);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
