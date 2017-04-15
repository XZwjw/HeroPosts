package first.nestedsliding.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import first.nestedsliding.R;
import first.nestedsliding.activity.SingleHeroActivity;
import first.nestedsliding.modle.LOLHero;

/**
 * Created by dell on 2016/9/23.
 */
public class HeroRecycleViewAdpter extends RecyclerView.Adapter<MyHeroViewHolder> {


    private Context mContext;
    private LayoutInflater mInflater;
    private List<LOLHero> mList;
    private MyHeroViewHolder mMyHeroViewHolder;

    public HeroRecycleViewAdpter(Context context, List<LOLHero> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public MyHeroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lol_item, parent, false);
        mMyHeroViewHolder = new MyHeroViewHolder(view);
        return mMyHeroViewHolder;
    }

    @Override
    public void onBindViewHolder(MyHeroViewHolder holder, int position) {
        final LOLHero lolHero = mList.get(position);
        /**
         * 获取图片        holder.imageHero.setImageBitmap("bitmap");
         */
        Glide.with(mContext).load(lolHero.getPhotoUri()).into(holder.imageHero);
        holder.imageHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SingleHeroActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",lolHero.getId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        holder.name.setText(lolHero.getName());
        holder.price.setText(lolHero.getPrice());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

class MyHeroViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageHero;
    public TextView name;
    public TextView price;
    public MyHeroViewHolder(View itemView) {
        super(itemView);
        init(itemView);
    }

    private void init(View view) {
        imageHero = (ImageView) view.findViewById(R.id.item_image);
        name = (TextView) view.findViewById(R.id.item_name);
        price = (TextView) view.findViewById(R.id.item_price);
    }
}
