package info.leftpi.aptviewinject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import info.leftpi.annotation.BindView;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textview)
    TextView mTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity_Bind.bind(this);
        mTextview.setText("this is apt");
    }
}
