package it.jaschke.alexandria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutTextView = (TextView) findViewById(R.id.about_text_view);
        aboutTextView.setText("this is just a test");
    }

}
