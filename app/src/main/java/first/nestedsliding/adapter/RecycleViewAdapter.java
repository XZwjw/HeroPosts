package first.nestedsliding.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import first.nestedsliding.R;

/**
 * Created by dell on 2016/9/22.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private Context mContext;
    private LayoutInflater mInflater;
    private String[] strings;
    private MyViewHolder myViewHolder;

    public RecycleViewAdapter(Context context, String[] strings) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.strings = strings;
        Log.d("TAG", "String.length :" + strings.length);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycle_view_item, parent, false);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemText.setText("item :" + position);
        Log.d("TAG",position+"");
    }

    @Override
    public int getItemCount() {
        return strings.length;
    }


}
class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView itemText;
    public MyViewHolder(View itemView) {
        super(itemView);
        itemText = (TextView) itemView.findViewById(R.id.item_Text);
    }
}
