package rivazdev.com.tma.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@DatabaseTable(tableName = "tasks")
public class Task extends BaseDaoEnabled<Task,Long>
{
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STARTED_AT = "started_at";
    public static final String COLUMN_ENDED_AT = "ended_at";
    public static final String COLUMN_BILLABLE = "billable";
    public static final String COLUMN_TASK_NAME = "task_name";
    public static final String COLUMN_TASK_NOTE="task_note";
    public static final String COLUMN_TIME_SPENT = "time_spent";

    @DatabaseField(columnName = COLUMN_ID, dataType = DataType.LONG, generatedId = true)
    public long id;

    @DatabaseField(columnName = COLUMN_STARTED_AT, dataType = DataType.DATE_LONG )
    public Date startedAt;

    @DatabaseField(columnName = COLUMN_ENDED_AT, dataType = DataType.DATE_LONG )
    public Date endedAt;

    @DatabaseField(columnName = COLUMN_BILLABLE, dataType = DataType.BOOLEAN_OBJ)
    public Boolean billable;

    @DatabaseField(columnName = COLUMN_TASK_NAME, dataType= DataType.STRING)
    public String taskName;

    @DatabaseField(columnName = COLUMN_TASK_NOTE, dataType= DataType.STRING)
    public String taskNote;

    @DatabaseField(columnName = COLUMN_TIME_SPENT, dataType= DataType.STRING)
    public String timeSpent;

    public Task()
    {

    }

    public static Date getNowGMT() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.setTime(new Date());
        return c.getTime();
    }

    public Task(String taskName, String taskNote)
    {
        this.startedAt= getNowGMT();
        this.taskName= taskName;
        this.taskNote= taskNote;
        this.billable=true;
        this.endedAt=null;
        this.timeSpent=null;
    }

    public Task(Date startedAt, String taskName, String taskNote, Boolean billable)
    {
        this.startedAt= startedAt;
        this.taskName= taskName;
        this.taskNote= taskNote;
        this.billable=billable;
        this.endedAt=null;
        this.timeSpent=null;

    }

    public void setStartedDate(Date startedAt  )
    {
        this.startedAt=startedAt;
    }

    public void setEndedDate(Date endedAt  )
    {
        this.endedAt=endedAt;
    }

    public void setTaskNote(String taskNote)
    {
        this.taskNote= taskNote;
    }



    public void setTaskName(String taskName)
    {
        this.taskName=taskName;
    }
    public void setTimeSpent (String timeSpent )
    {
        this.timeSpent=timeSpent;
    }

}