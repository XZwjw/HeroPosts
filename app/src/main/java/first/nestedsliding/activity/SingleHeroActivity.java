package first.nestedsliding.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import first.nestedsliding.R;
import first.nestedsliding.fragment.lol.LOlFragment;
import first.nestedsliding.itf.SingleHeroCallBack;
import first.nestedsliding.modle.LOLHero;
import first.nestedsliding.modle.Passive;
import first.nestedsliding.modle.Skill;
import first.nestedsliding.modle.Skin;
import first.nestedsliding.modle.Spell;
import first.nestedsliding.util.LoadSingleHero;

/**
 * Created by dell on 2016/10/26.
 */
public class SingleHeroActivity extends AppCompatActivity implements SingleHeroCallBack{

    private final static String TAG = "SingleHeroActivity";


    //皮肤、被动、技能Q、W、E、R
    private ImageView mImageSkin,mImagePossive,mImageQ,mImageW,mImageE,mImageR;
    //被动、技能Q、W、E、R(名称、介绍)
    private TextView mNamePossive,mNameQ,mNameW,mNameE,mNameR,mTextPossive,mTextQ,mTextW,mTextE,mTextR,mLevelTipQ,mLevelTipW,mLevelTipE,mLevelTipR;
    //自己使用该英雄、敌人使用该英雄（使用技巧）
    private TextView mUseTheir,mUseEnemy,mNameTheir,mNameEnemy;
    //背景故事
    private TextView mBackground,mMore;
    private Toolbar mToolBar;
    private String id;
    private ImageView playImage;
    private FloatingActionButton mFloatingActionButton;

    private static int i = 0;   //皮肤数组下标



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_hero);
        Bundle bundle = getIntent().getExtras();
        id =  bundle.getString("id");
        Log.d("TAG1", "singleHeroActivity onCreate id:" + id);
        init();
        LoadSingleHero loadSingleHero = new LoadSingleHero(this,id);
        loadSingleHero.execute();

    }


    private void init() {

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.float_button);

        mToolBar = (Toolbar) findViewById(R.id.toolbar_single);
        mImageSkin = (ImageView) findViewById(R.id.image_skin_single);
        mImagePossive = (ImageView) findViewById(R.id.image_passive_single);
        mImageQ = (ImageView) findViewById(R.id.image_spellQ_single);
        mImageW = (ImageView) findViewById(R.id.image_spellW_single);
        mImageE = (ImageView) findViewById(R.id.image_spellE_single);
        mImageR = (ImageView) findViewById(R.id.image_spellR_single);

        mNamePossive = (TextView) findViewById(R.id.name_passive_single);
        mNameQ = (TextView) findViewById(R.id.name_spellQ_single);
        mNameW = (TextView) findViewById(R.id.name_spellW_single);
        mNameE = (TextView) findViewById(R.id.name_spellE_single);
        mNameR = (TextView) findViewById(R.id.name_spellR_single);

        mTextPossive = (TextView) findViewById(R.id.text_passive_single);
        mTextQ = (TextView) findViewById(R.id.text_spellQ_single);
        mTextW = (TextView) findViewById(R.id.text_spellW_single);
        mTextE = (TextView) findViewById(R.id.text_spellE_single);
        mTextR = (TextView) findViewById(R.id.text_spellR_single);

        mLevelTipQ = (TextView) findViewById(R.id.leveltip_spellQ_single);
        mLevelTipW = (TextView) findViewById(R.id.leveltip_spellW_single);
        mLevelTipE = (TextView) findViewById(R.id.leveltip_spellE_single);
        mLevelTipR = (TextView) findViewById(R.id.leveltip_spellR_single);

        mBackground = (TextView) findViewById(R.id.backgroundStory_single);
        mMore = (TextView) findViewById(R.id.open_single);
        mNameTheir = (TextView) findViewById(R.id.their_name);
        mNameEnemy = (TextView) findViewById(R.id.enemy_name);
        mUseTheir = (TextView) findViewById(R.id.their_use);
        mUseEnemy = (TextView) findViewById(R.id.enemy_use);

//        playImage = (ImageView) findViewById(R.id.video_image);
    }

    @Override
    public void assignment(final LOLHero lolHero) {



        final Skin[] skinArray = lolHero.getSkins();

        Skin skin = null;
        if(skinArray != null || skinArray[0] != null) {
            skin = skinArray[0];
        }
        if(skin != null) {
            mToolBar.setTitle(skin.getName());
        }
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spell[] spellArray = lolHero.getSpells();
        Spell spell1 = spellArray[0];
        Spell spell2 = spellArray[1];
        Spell spell3 = spellArray[2];
        Spell spell4 = spellArray[3];
        Passive passive = lolHero.getPassive();
        Skill skill = lolHero.getSkill();



        mBackground.setText(lolHero.getBlurb());
        Log.d("TAG", "mBackground.getText().toString():" + mBackground.getText().toString());
        Log.d("TAG", "lolHero.getBlurb():" + lolHero.getBlurb());
        Log.d("TAG", "lolHero.getLore():" + lolHero.getLore());
        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "mMore点击了");
                if (mBackground.getText().toString().equals(lolHero.getBlurb())) {
                    mBackground.setText(lolHero.getLore());
                } else {
                    mBackground.setText(lolHero.getBlurb());
                }
                if (mMore.getText().toString().equals("了解更多...")) {
                    mMore.setText("收起...");
                } else {
                    mMore.setText("了解更多...");
                }

            }
        });

        if(skin != null) {
            Glide.with(this).load(skinArray[0].getBigImageUrl()).into(mImageSkin);

            if(skinArray.length > 1) {

                mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Glide.with(getApplication()).load(skinArray[(++i)%skinArray.length].getBigImageUrl()).into(mImageSkin);
                        mToolBar.setTitle(skinArray[i%skinArray.length].getName());
                    }
                });
            }
        }
        Glide.with(this).load(passive.getImageUrl()).into(mImagePossive);

        Glide.with(this).load(spell1.getImageUrl()).into(mImageQ);
        Glide.with(this).load(spell2.getImageUrl()).into(mImageW);
        Glide.with(this).load(spell3.getImageUrl()).into(mImageE);
        Glide.with(this).load(spell4.getImageUrl()).into(mImageR);

        mNamePossive.setText(passive.getName());
        mTextPossive.setText(passive.getDescription());

        mNameQ.setText(spell1.getName() + " ");
        mTextQ.setText(spell1.getTooltip());
        StringBuilder sb1 = new StringBuilder();
        for(int i = 0;i < spell1.getLabel().length;i++) {
            if(i == (spell1.getLabel().length - 1)) {
                sb1.append(spell1.getLabel()[i]+":"+spell1.getEffect()[i]);
            } else {
                sb1.append(spell1.getLabel()[i] + ":" + spell1.getEffect()[i] + "\n");
            }
        }
        mLevelTipQ.setText(sb1.toString());
 
        mNameW.setText(spell2.getName()+" ");
        mTextW.setText(spell2.getTooltip());
        StringBuilder sb2 = new StringBuilder();
        for(int i = 0;i < spell2.getLabel().length;i++) {
            if(i == (spell2.getLabel().length - 1)) {
                sb2.append(spell2.getLabel()[i]+":"+spell2.getEffect()[i]);
            } else {
                sb2.append(spell2.getLabel()[i] + ":" + spell2.getEffect()[i] + "\n");
            }
        }
        mLevelTipW.setText(sb2.toString());
        

        mNameE.setText(spell3.getName()+" ");
        mTextE.setText(spell3.getTooltip());
        StringBuilder sb3 = new StringBuilder();
        for(int i = 0;i < spell3.getLabel().length;i++) {
            if(i == (spell3.getLabel().length - 1)) {
                sb3.append(spell3.getLabel()[i]+":"+spell3.getEffect()[i]);
            } else {
                sb3.append(spell3.getLabel()[i] + ":" + spell3.getEffect()[i] + "\n");
            }
        }
        mLevelTipE.setText(sb3.toString());

        mNameR.setText(spell4.getName() + " ");
        mTextR.setText(spell4.getTooltip());
        StringBuilder sb4 = new StringBuilder();
        for(int i = 0;i < spell4.getLabel().length;i++) {
            if(i == (spell4.getLabel().length - 1)) {
                sb4.append(spell4.getLabel()[i]+":"+spell4.getEffect()[i]);
            } else {
                sb4.append(spell4.getLabel()[i] + ":" + spell4.getEffect()[i] + "\n");
            }
        }
        mLevelTipR.setText(sb4.toString());

        mNameTheir.setText("当你使用"+lolHero.getName());
        mNameEnemy.setText("当敌人使用"+lolHero.getName());
        mUseTheir.setText(skill.getAllytips());
        mUseEnemy.setText(skill.getEnemytips());



    }

    @Override
    protected void onRestart() {
        if(LOlFragment.HAVE_NETWORK) {
            LoadSingleHero loadSingleHero = new LoadSingleHero(this,id);
            loadSingleHero.execute();
        }
        Log.d(TAG,"onRestart");
        super.onRestart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
