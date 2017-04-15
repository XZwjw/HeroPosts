package first.nestedsliding.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import first.nestedsliding.R;
import first.nestedsliding.activity.MainActivity;

/**
 * Created by dell on 2017/2/24.
 */
public class AboutUsFragment extends Fragment {

    private Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about,null,false);
        init(view);
        mToolbar.setNavigationIcon(R.drawable.side_navigation);
        mToolbar.setTitle("关于我");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mNavigationOpened) {
                    MainActivity.closeNavigation();
                } else {
                    MainActivity.openNavigation();
                }

            }
        });
        return view;
    }

    private void init(View view ) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_about);
    }
}
