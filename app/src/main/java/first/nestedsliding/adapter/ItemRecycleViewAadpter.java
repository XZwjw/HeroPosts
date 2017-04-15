package first.nestedsliding.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import first.nestedsliding.R;
import first.nestedsliding.modle.LOLItem;

/**
 * Created by dell on 2016/10/20.
 */
public class ItemRecycleViewAadpter extends RecyclerView.Adapter<MyItemHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<LOLItem> mItemList;
    private Dialog mDialog;

    public ItemRecycleViewAadpter(Context context,ArrayList<LOLItem> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mItemList = list;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext);
    }

    @Override
    public MyItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lol_item,parent,false);
        MyItemHolder mMyItemHolder = new MyItemHolder(view);
        return mMyItemHolder;
    }

    @Override
    public void onBindViewHolder(MyItemHolder holder, int position) {
        final LOLItem item = mItemList.get(position);
        Glide.with(mContext).load(item.getImageUrl()).into(holder.mPhotoImage);
        holder.mPhotoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = mInflater.inflate(R.layout.item_introduce, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.item_introduce_image);
                Glide.with(mContext).load(item.getImageUrl()).into(imageView);
                TextView textView = (TextView) view.findViewById(R.id.item_introduce_name);
                textView.setText(item.getName());
                TextView sellText = (TextView) view.findViewById(R.id.item_introduce_sell);
                sellText.setText("出售价格:" + item.getSell());TextView description = (TextView) view.findViewById(R.id.item_introduce_description);
                description.setText(item.getDescription());
                TextView plaintext = (TextView) view.findViewById(R.id.item_introduce_plaintText);
                plaintext.setText(item.getPlaintext());
                mDialog.setContentView(view);
                mDialog.create();
                mDialog.show();

            }
        });
        holder.mName.setText(item.getName());
        holder.mPrice.setText(item.getPrice());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}

class MyItemHolder extends RecyclerView.ViewHolder{

    public ImageView mPhotoImage;  //物品图片
    public TextView mName;         //物品名称
    public TextView mPrice;        //物品价格

    public MyItemHolder(View itemView) {
        super(itemView);
        init(itemView);
    }

    private void init(View view) {
        mPhotoImage = (ImageView) view.findViewById(R.id.item_image);
        mName = (TextView) view.findViewById(R.id.item_name);
        mPrice = (TextView) view.findViewById(R.id.item_price);
    }
}
