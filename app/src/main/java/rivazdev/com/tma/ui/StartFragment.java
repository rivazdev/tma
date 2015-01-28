package rivazdev.com.tma.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import rivazdev.com.tma.R;
import rivazdev.com.tma.db.DBHandler;
import rivazdev.com.tma.model.Task;


public class StartFragment extends BaseFragment implements OnClickListener
	,DatePicker.OnDateChangedListener,TimePicker.OnTimeChangedListener {

	private Spinner mSpinner;
	private DatePicker mDatePicker;
	private TimePicker mTimePicker;
	private EditText noteEditText;
	private RadioButton billableRadio;
	private RadioButton nonBillableRadio;
	private Button startButton;
	private int year,month,day,hour,minute;
	private boolean isBillable=true;
	private String taskName,taskNote;
	ArrayAdapter dataAdapter;
	
	public static StartFragment newInstance()
	{
		StartFragment frag = new StartFragment();
		return frag;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                     Bundle savedInstanceState) {
	View rootView = inflater.inflate(R.layout.fragment_my, container, false);
	
	mSpinner= (Spinner) rootView.findViewById(R.id.spinner2);
	noteEditText = (EditText) rootView.findViewById(R.id.note_edit_text );
	mDatePicker= (DatePicker) rootView.findViewById(R.id.datePicker);
	mTimePicker= (TimePicker) rootView.findViewById(R.id.timePicker);
	billableRadio= (RadioButton) rootView.findViewById(R.id.bill_radio);
	nonBillableRadio=(RadioButton) rootView.findViewById(R.id.non_bill_radio);
	startButton= (Button) rootView.findViewById(R.id.start_task);
	
	
	spinnerListener();
	final Calendar c = Calendar.getInstance();
	year = c.get(Calendar.YEAR);
	month = c.get(Calendar.MONTH);
	day = c.get(Calendar.DAY_OF_MONTH);
	hour=c.get(Calendar.HOUR_OF_DAY);
	minute=c.get(Calendar.MINUTE);
	mDatePicker.init(year,month,day,this);
	mDatePicker.setDescendantFocusability(mDatePicker.FOCUS_BLOCK_DESCENDANTS);
	mTimePicker.setCurrentHour(hour);
	mTimePicker.setCurrentMinute(minute);
	mTimePicker.setOnTimeChangedListener(this);
	mTimePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
	startButton.setOnClickListener(this);
	
	
	
	return rootView;
	}
	private void spinnerListener()
	{
	Resources res = getResources();
	String[] planets = res.getStringArray(R.array.project_name);
	ArrayList<String> mylist =new ArrayList<String>(Arrays.asList(planets));
	mylist.add(0,"Select");
	int lastElement = mylist.size();
	mylist.add(lastElement,"<<NEW>>");
	
	dataAdapter = new ArrayAdapter<String>(getActivity(),R.layout.simpletextlayout, mylist);
	
	mSpinner.setAdapter(dataAdapter);
	
	mSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view,
	                           int position, long id) {
	
	    taskName= (String) dataAdapter.getItem(position);
	
	}
	
	    @Override
	    public void onNothingSelected(AdapterView<?> parent) {
	
	
	    }
	
	});
	}
	private boolean getBillable()
	{
	if(billableRadio.isChecked())
	{
	    isBillable=true;
	    return isBillable;
	}
	if(nonBillableRadio.isChecked())
	{
	    isBillable=false;
	    return isBillable;
	}
	return  true;
	}
	private String getNote()
	{
	taskNote= noteEditText.getText().toString().trim();
	
	return taskNote;
	}
	
	@Override
	public void onClick(View view) {
	
	if(view.getId()==R.id.start_task)
	{
	
	    boolean mBillable=getBillable();
	
	
	
	
	    Calendar calendar = Calendar.getInstance();
	    calendar.clear();
	    calendar.set(Calendar.MONTH, month);
	    calendar.set(Calendar.YEAR, year);
	    calendar.set(Calendar.DAY_OF_MONTH,day);
	    calendar.set(Calendar.HOUR_OF_DAY,hour);
	    calendar.set(Calendar.MINUTE,minute);
	    Date startDate = calendar.getTime();
	
	    Task newTask = new Task(startDate,taskName,getNote(),mBillable
	           );
	    newTask.billable=mBillable;
	    newTask.setDao((Dao<Task, Long>)  DBHandler.getHelper(getActivity())
	            .getDao(Task.class));
	
	
	    try {
	        newTask.create();
	    } catch (SQLException e) {
	        Log.e(getClass().getSimpleName(), "SQL: " + e.getMessage());
	    }
	
	    Toast.makeText(getActivity(), "New Task Created ", Toast.LENGTH_LONG).show();
	
	
	}
	
	}
	
	
	@Override
	public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
	this.year=year;
	this.month=month;
	this.day=day;
	
	}
	
	@Override
	public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
	this.hour=hour;
	this.minute=minute;
	
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
}
