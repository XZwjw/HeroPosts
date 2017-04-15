package first.nestedsliding.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import first.nestedsliding.R;
import first.nestedsliding.fragment.lol.LOlFragment;

/**
 * Created by dell on 2016/11/13.
 */
public class DisConnectionActivity extends Activity {

    private TextView mDisConnectionTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disconnection);
        init();

        mDisConnectionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LOlFragment.HAVE_NETWORK) {
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        mDisConnectionTextView = (TextView) findViewById(R.id.reload_disconnection);
    }
}
