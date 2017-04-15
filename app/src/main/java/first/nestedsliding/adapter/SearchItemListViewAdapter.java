package first.nestedsliding.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import first.nestedsliding.R;
import first.nestedsliding.modle.LOLItem;

/**
 * Created by dell on 2016/11/30.
 */
public class SearchItemListViewAdapter extends BaseAdapter{

    private final static String TAG = "SearchItemListViewAdapter";

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mList;     //数据列表

    private HashMap<String,LOLItem> mItemMap;
    private Dialog mDialog;
//    private List<String> mBackList; //原数据列表
//    private MyFilter mFilter;


    public SearchItemListViewAdapter(Context context,List<String> list,HashMap<String,LOLItem> itemMap){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        if(list == null) {
            mList = new ArrayList<>();
        } else {
            mList = list;
        }
        mItemMap = itemMap;
        mDialog = new Dialog(context);
    }

    @Override
    public int getCount() {

        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        final ViewHolder mViewHolder;
        if(convertView == null) {
            view = mInflater.inflate(R.layout.search_hero_item,null);
            mViewHolder = new ViewHolder(view);
            view.setTag(mViewHolder);
        } else {
            view = convertView;
            mViewHolder = (ViewHolder) view.getTag();
        }
        mViewHolder.mTextView.setText(mList.get(position));
        if(!mViewHolder.mTextView.getText().equals("主人,没找到该物品")) {
            mViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LOLItem lolItem = mItemMap.get(mViewHolder.mTextView.getText().toString());
                    View view1 = mInflater.inflate(R.layout.item_introduce, null);
                    ImageView imageView = (ImageView) view1.findViewById(R.id.item_introduce_image);
                    Glide.with(mContext).load(lolItem.getImageUrl()).into(imageView);
                    TextView name = (TextView) view1.findViewById(R.id.item_introduce_name);
                    name.setText(lolItem.getName());
                    TextView sell = (TextView) view1.findViewById(R.id.item_introduce_sell);
                    sell.setText("出售价格: "+lolItem.getSell());
                    TextView plaint = (TextView) view1.findViewById(R.id.item_introduce_plaintText);
                    plaint.setText(lolItem.getPlaintext());
                    TextView description = (TextView) view1.findViewById(R.id.item_introduce_description);
                    description.setText(lolItem.getDescription());
                    mDialog.setContentView(view1);
                    mDialog.create();
                    mDialog.show();

                }
            });
        }


        return view;
    }


    class ViewHolder {
        TextView mTextView;
        public ViewHolder(View view) {
            init(view);
        }
        private void init(View view) {
            mTextView = (TextView) view.findViewById(R.id.text_search_item);
        }
    }
}
