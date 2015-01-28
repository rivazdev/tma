package rivazdev.com.tma.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;


import java.util.List;

import rivazdev.com.tma.R;
import rivazdev.com.tma.adapter.TaskAdapter;
import rivazdev.com.tma.db.DBHandler;
import rivazdev.com.tma.loader.BaseListLoader;
import rivazdev.com.tma.model.Task;

/**
 * Created by ricardo on 7/6/14.
 */
public class OpenTasksFragment extends BaseFragment implements
        LoaderCallbacks<List<Task>> {


	ListView listView;
    TaskAdapter adapter;
	Dao<Task,Long> taskDao;
	
	
	public static  OpenTasksFragment newInstance()
	{
		OpenTasksFragment frag= new OpenTasksFragment();
		return frag;
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		taskDao = (Dao<Task, Long>) DBHandler.getHelper(getActivity())
                .getDao(Task.class);
		
		adapter = new TaskAdapter(getActivity());
        

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.open_tasks_layout, container, false);
		
		listView = (ListView) rootView.findViewById(R.id.opentaskslist);
		getLoaderManager().initLoader(0, null, this);
		
		return rootView;
	}


	



	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        getActivityCallBacks().setItemSelected(position);
        adapter.notifyDataSetChanged();
        
    }

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Fragment Never goes into pause using ViewPager
	 */
	@Override
	public void onResume() {
		super.onResume();
		Log.e(getClass().getSimpleName(), "onResume");
	}


	@Override
	public Loader<List<Task>> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated methoda stub
		 return new BaseListLoader<Task>(getActivity(), taskDao,
				null, null, Task.COLUMN_ID,false);
	}


	@Override
	public void onLoadFinished(Loader<List<Task>> loader, List<Task> data) {
		
		adapter.setData(data);
		listView.setAdapter(adapter);
		Toast t = Toast.makeText(getActivity(), "hello: ", Toast.LENGTH_LONG);
		t.show();
		
	}


	@Override
	public void onLoaderReset(Loader<List<Task>> arg0) {
		adapter.setData(null);
		listView.setAdapter(adapter);
		
	}
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if(menuVisible){
			getLoaderManager().restartLoader(0, null, this);
		}
	}


	
	
}
