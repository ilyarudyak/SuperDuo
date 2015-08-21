package barqsoft.footballscores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import java.io.IOException;

import barqsoft.footballscores.utils.FileUtils;


public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutTextView = (TextView) findViewById(R.id.about_text_view);
        try {
            String aboutString = Html.fromHtml(FileUtils.readAboutFile(this)).toString();
            aboutTextView.setText(aboutString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
