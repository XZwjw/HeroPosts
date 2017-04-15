package first.nestedsliding.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by dell on 2016/9/23.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitle;
    private List<Fragment> mFragmentList;
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitle) {
        super(fm);
        this.mTitle = mTitle;
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
