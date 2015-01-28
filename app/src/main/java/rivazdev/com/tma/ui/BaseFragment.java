package rivazdev.com.tma.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public abstract class BaseFragment extends Fragment {

	protected ActivityCallBacks myActivityCallbacks;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (ActivityCallBacks.class.isInstance(activity)) {
			this.myActivityCallbacks = (ActivityCallBacks) activity;
		}
	}

	public ActivityCallBacks getActivityCallBacks() {
		return myActivityCallbacks;
	}

	public void setActivityCallBacks(
			ActivityCallBacks myActivityCallbacks) {
		this.myActivityCallbacks = myActivityCallbacks;
	}


	public void onRestoreInstanceState(Bundle savedInstanceState) {
	}

	public abstract void onBackPressed();
	
	final private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			onListItemClick((ListView) parent, v, position, id);
		}
	};
	
	public void onListItemClick(ListView l, View v, int position, long id) {
	}
	


}

