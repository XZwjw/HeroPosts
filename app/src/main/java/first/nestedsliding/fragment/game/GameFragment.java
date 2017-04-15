package first.nestedsliding.fragment.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import first.nestedsliding.R;
import first.nestedsliding.activity.MainActivity;

/**
 * Created by dell on 2016/10/7.
 */
public class GameFragment extends Fragment {


    private Toolbar mToolbar;
    private CardView[] mCardViews;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_game,container,false);
        init(view);
        mToolbar.setNavigationIcon(R.drawable.side_navigation);
        mToolbar.setTitle("Game");
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

    private void init(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolBar_game);

    }


}
