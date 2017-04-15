package first.nestedsliding.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import first.nestedsliding.R;
import first.nestedsliding.modle.Book;

/**
 * Created by dell on 2016/10/8.
 */
public class BookShelfRecycleViewAdapter extends RecyclerView.Adapter<MyBookShelfViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<Book> mBookList;
    private MyBookShelfViewHolder myBookShelfViewHolder;


    public BookShelfRecycleViewAdapter(Context context,List<Book> bookList) {
        this.mContext = context;
        this.mBookList = bookList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyBookShelfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.book_item,parent,false);
        myBookShelfViewHolder = new MyBookShelfViewHolder(view);
        return myBookShelfViewHolder;
    }

    @Override
    public void onBindViewHolder(MyBookShelfViewHolder holder, int position) {
        /**
         * 设置图片
         */
//        holder.mImageView.setImageBitmap();
        holder.mBookName.setText(mBookList.get(position).getName());
        holder.mAuthor.setText(mBookList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}

class MyBookShelfViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImageView;
    public TextView mBookName;
    public TextView mAuthor;
    public MyBookShelfViewHolder(View itemView) {
        super(itemView);
        init(itemView);
    }

    private void init(View itemView) {
        mImageView = (ImageView) itemView.findViewById(R.id.book_image);
        mBookName = (TextView) itemView.findViewById(R.id.book_name);
        mAuthor = (TextView) itemView.findViewById(R.id.book_author);
    }
}
