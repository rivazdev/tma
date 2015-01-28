package rivazdev.com.tma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import rivazdev.com.tma.R;
import rivazdev.com.tma.model.Task;

public class TaskAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<Task> tasks;
	public TaskAdapter(Context context) {
		
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	public void setData(List<Task> data)
	{
		if(data !=null)
		{
			tasks=(ArrayList<Task>) data;
		}
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		
		if(convertView ==null)
		{
			view = mInflater.inflate(R.layout.listview_text_layout, null);
		}
		else{
			view = convertView;
		}
		
		if(tasks!=null){
			Task task = tasks.get(position);
		
			TextView text1 = (TextView)view.findViewById(R.id.mytext1);
			TextView text2 = (TextView)view.findViewById(R.id.mytext2);
			
			
			text1.setText(""
					+ task.startedAt.toString()+" | "+task.taskName);
			
			text2.setText(""+task.taskNote);
		}
		return view;
	}
	@Override
	public int getCount() {
		return tasks.size();
	}
	@Override
	public Object getItem(int position) {
		return tasks.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	

	
}
