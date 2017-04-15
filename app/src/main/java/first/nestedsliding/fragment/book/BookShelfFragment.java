package first.nestedsliding.fragment.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import first.nestedsliding.R;
import first.nestedsliding.adapter.BookShelfRecycleViewAdapter;
import first.nestedsliding.modle.Book;
import first.nestedsliding.shap.ItemDivider;

/**
 * Created by dell on 2016/10/8.
 * 书架Fragment(BookFragment)
 */
public class BookShelfFragment extends Fragment{

    private TextView collectionQuantity;    //已收藏的书本数; //已收藏X本
    private TextView totalCapacity;         //书本总容量数;   //总容量X本
    private RecyclerView recyclerView;      //书架列
    private BookShelfRecycleViewAdapter adapter;
    private List<Book> mBookList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_shelf,container,false);
        init(view);
        Log.d("TAG","开始执行1");
        /**
         * 获取mBookList
         */
        mBookList = getBookList();
        adapter = new BookShelfRecycleViewAdapter(getContext(),mBookList);
        recyclerView.setAdapter(adapter);
        Log.d("TAG", "开始执行2");

        /**
         *  ScrollView 嵌套 RecyclerView 高度自适应 (解决scrollView箱套recycleView出现的滑动卡顿现象)
         *  recycleView嵌套滚动不启动
         */
        recyclerView.setNestedScrollingEnabled(false);


        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        ItemDivider itemDivider = new ItemDivider(getContext(),R.drawable.item_divider);
        recyclerView.addItemDecoration(itemDivider);
        collectionQuantity.setText("已收藏" + mBookList.size() + "本");
        totalCapacity.setText("总容量150本");
        Log.d("TAG","bookshelf"+"调用了一次");
        return view;
    }

    private void init(View view) {
        collectionQuantity = (TextView) view.findViewById(R.id.collectionQuantity);
        totalCapacity = (TextView) view.findViewById(R.id.totalCapacity);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView_bookShelf);
    }

    public List<Book> getBookList() {
        List<Book> mList = new ArrayList<>();

        for(int i = 0;i < 30;i++) {
            Book book = new Book();
            book.setName("书名"+i);
            book.setAuthor("作者" + i);
            mList.add(book);
        }
        return mList;
    }
}
