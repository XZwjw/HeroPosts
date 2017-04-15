package first.nestedsliding.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import first.nestedsliding.R;
import first.nestedsliding.fragment.AboutUsFragment;
import first.nestedsliding.fragment.lol.LOlFragment;

public class MainActivity extends AppCompatActivity{

    private static NavigationView mNavigationView;
    private static DrawerLayout mDrawerLayout;
    public static boolean mNavigationOpened = false;
    public  FragmentManager fragmentManager;
    private static LOlFragment fragment = new LOlFragment();
//    private static BookFragment bookFragment = new BookFragment();
//    private static GameFragment gameFragment = new GameFragment();
    private static AboutUsFragment aboutFragment = new AboutUsFragment();

//    private ImageView mModifyImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.main);
        Log.d("TAG1","MainActivity  onCreate");
        init();

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        /**
         * mDrawerLayout的状态监听事件
         */
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                mNavigationOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mNavigationOpened = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        /**
         * mNavigationView的菜单Menu监听事件
         */
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.item_lol:

                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                        mDrawerLayout.closeDrawer(mNavigationView);
                        break;
//                    case R.id.item_book:
//                        fragmentManager.beginTransaction().replace(R.id.content_frame,bookFragment).commit();
//                        mDrawerLayout.closeDrawer(mNavigationView);
//                        break;
//                    case R.id.item_game:
//                        fragmentManager.beginTransaction().replace(R.id.content_frame, gameFragment).commit();
//                        mDrawerLayout.closeDrawer(mNavigationView);
//                        break;
//                    case R.id.item_version_information:
//
//                        break;
                    case R.id.item_about:
                        fragmentManager.beginTransaction().replace(R.id.content_frame, aboutFragment).commit();
                        mDrawerLayout.closeDrawer(mNavigationView);
                        break;

                }
                return true;
            }
        });

//        /**
//         * 修改图片与名字
//         */
//        mModifyImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }



    private void init() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
//        mModifyImage = (ImageView) findViewById(R.id.modify_header);
        fragmentManager = getSupportFragmentManager();

    }



    /**
     * closeNavigation关闭侧边栏
     */
    public static void closeNavigation() {
        if(mNavigationOpened) {
            mDrawerLayout.closeDrawer(mNavigationView);

        }
    }

    /**
     * openNavigation打开侧边栏
     */
    public static void openNavigation() {
        if(!mNavigationOpened) {
            mDrawerLayout.openDrawer(mNavigationView);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the header; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }



}
