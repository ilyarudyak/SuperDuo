package barqsoft.footballscores.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import barqsoft.footballscores.R;

/**
 * Created by yehya khaled on 2/26/2015.
 */
public class ViewHolder {

    public TextView time;
    public TextView homeName;
    public TextView awayName;
    public ImageView homeCrest;
    public ImageView awayCrest;
    public TextView score;
    public String matchId;

    public ViewHolder(View view) {

        time = (TextView) view.findViewById(R.id.time_text_view);
        homeName = (TextView) view.findViewById(R.id.home_name);
        awayName = (TextView) view.findViewById(R.id.away_name);
        homeCrest = (ImageView) view.findViewById(R.id.home_crest);
        awayCrest = (ImageView) view.findViewById(R.id.away_crest);
        score = (TextView) view.findViewById(R.id.score_text_view);
    }
}
