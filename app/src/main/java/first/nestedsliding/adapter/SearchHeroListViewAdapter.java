package first.nestedsliding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import first.nestedsliding.R;

/**
 * Created by dell on 2016/11/29.
 */
public class SearchHeroListViewAdapter extends BaseAdapter{
    private final static String TAG = "SearchHeroListViewAdapter";
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mList;
//    private List<String> mBackList; //原数据列表

    public SearchHeroListViewAdapter(Context context,List<String> list){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        if(list != null) {
            mList = list;
        } else {
            mList = new ArrayList<>();
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder mViewHolder;
        if(convertView == null) {
            view = mInflater.inflate(R.layout.search_hero_item,null);
            mViewHolder = new ViewHolder(view);
            view.setTag(mViewHolder);
        } else {
            view = convertView;
            mViewHolder = (ViewHolder) view.getTag();
        }
        mViewHolder.mTextView.setText(mList.get(position));


        return view;
    }


    /**
     * 过滤
     * @return
     */
//    @Override
//    public Filter getFilter() {
//        if(mFilter != null) {
//            mFilter = new MyFilter();
//        }
//        return mFilter;
//    }
//
//    class MyFilter extends Filter {
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults results = new FilterResults();
//
//            results.values = lists;
//            results.count = lists.size();
//            return null;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            mList = (List<String>) results.values;
//            if(results.count >0) {
//                notifyDataSetChanged();
//                Log.d(TAG, "notifyDataSetChanged");
//            } else {
//                notifyDataSetInvalidated();
//                Log.d(TAG,"notifyDataSetInvalidated");
//            }
//        }
//    }

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
