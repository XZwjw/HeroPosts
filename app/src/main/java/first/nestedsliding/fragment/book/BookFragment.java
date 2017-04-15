package first.nestedsliding.fragment.book;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import first.nestedsliding.R;
import first.nestedsliding.activity.MainActivity;

/**
 * Created by dell on 2016/10/8.
 * 书本Fragment(包含 BookShelfFragment(书架)、FindFragment)
 */
public class BookFragment extends Fragment {


    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private Toolbar toolbar;
    private BookShelfFragment bookShelfFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_book,container,false);
        init(view);
        toolbar.setTitle("Book");
        toolbar.setTitleTextColor(Color.WHITE);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        setHasOptionsMenu(true);
        toolbar.setNavigationIcon(R.drawable.side_navigation);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mNavigationOpened) {
                    MainActivity.closeNavigation();
                } else {
                    MainActivity.openNavigation();
                }

            }
        });
        bookShelfFragment = new BookShelfFragment();
        Log.d("TAG", "进onCreate了");
        mFragmentList = new ArrayList<>();
        mFragmentList.add(bookShelfFragment);
//        mFragmentList.add(bookShelfFragment);
//        mFragmentList.add(bookShelfFragment);
//        mFragmentList.add(bookShelfFragment);
        Log.d("TAG","mFragmentList.size :"+mFragmentList.size());
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.d("TAG","ViewPager调用了一次");
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });

        return view;

    }

    private void init(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolBar_book);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager_book);

    }


}
