package barqsoft.footballscores;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import barqsoft.footballscores.utils.MiscUtils;

/**
 * Created by yehya khaled on 2/27/2015.
 */
public class PagerFragment extends Fragment {

    // length of our time frame in days and
    // number of pages in our view pager
    public static final int NUM_PAGES = 5;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);

        // we use nested fragments here with min API 17
        ScoresPageAdapter adapter = new ScoresPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(MainActivity.CURRENT_FRAGMENT);


        return rootView;
    }

    /**
     * We use here FragmentPagerAdapter instead of FragmentStatePagerAdapter.
     * From documentation: "This is best when navigating between sibling
     * screens representing a fixed, small number of pages."
     * */
    private class ScoresPageAdapter extends FragmentPagerAdapter {

        public final String TAG = ScoresPageAdapter.class.getSimpleName();

        public ScoresPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Fragment getItem(int position) {
            return MainFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            Log.d(TAG, "I'm finally calling getPageTitle()" + MiscUtils.getDayName(getActivity(), position));
            return MiscUtils.getDayName(getActivity(), position);
        }

    }
}
