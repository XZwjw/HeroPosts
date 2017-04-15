package first.nestedsliding.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import first.nestedsliding.R;

/**
 * Created by dell on 2016/11/4.
 * 无网Fragment
 */
public class DisConnectionFragment extends Fragment {
    private TextView mReLoadText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.disconnection,container,false);
        init(view);

        /**
         * 重新加载监听事件
         */
        mReLoadText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void init(View view) {

        mReLoadText = (TextView) view.findViewById(R.id.reload_disconnection);
    }

}
