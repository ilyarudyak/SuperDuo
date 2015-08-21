package barqsoft.footballscores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import barqsoft.footballscores.api.BootReceiver;
import barqsoft.footballscores.api.ScoresService;

public class MainActivity extends AppCompatActivity {

    public static int selectedMatchId;

    // we have 5 pages numbered from 0 to 4
    // so today has position == 2
    public static final int CURRENT_FRAGMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, new PagerFragment())
                    .commit();
        }

        // start service to get data from API
        BootReceiver.scheduleAlarms(this);
    }


    // ----------------- menu ---------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent start_about = new Intent(this, AboutActivity.class);
            startActivity(start_about);
            return true;
        } else if (id == R.id.action_refresh) {
            Intent serviceIntent = new Intent(this, ScoresService.class);
            startService(serviceIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
