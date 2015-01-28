package rivazdev.com.tma.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class BaseListLoader<D> extends AsyncTaskLoader<List<D>> {
	private List<D> cache;
	protected Dao<D, Long> dao;
	private String whereColumn;
	private String WhereString;
	private String orderBy;
	private boolean accending;
	
	public BaseListLoader(Context context, Dao<D, Long> dao, String whereColumn, String whereString,
                          String orderBy, boolean accending) {
		super(context);
		this.whereColumn= whereColumn;
		this.WhereString= whereString;
		this.orderBy= orderBy;
		this.accending= accending;
		this.dao=dao;
		
	}

	@Override
	public List<D> loadInBackground() {
		if (dao != null) {
			try {

				QueryBuilder<D, Long> aQb = dao.queryBuilder();
				if(whereColumn==null || WhereString==null)
				{
		            aQb.orderBy(orderBy,accending);
				}
				else
				{
					aQb.where().eq(whereColumn	, WhereString);
		            aQb.orderBy(orderBy,accending);
				}
				
	            this.cache= aQb.query();
			} catch (SQLException e) {
				Log.e(getClass().getSimpleName(), e.getMessage(), e);
				this.cache=null;
			}
		}else{
			Log.v(getClass().getSimpleName(), "DAO is NULL!!");
		}
		return cache;
	}

	@Override
	public void deliverResult(List<D> data) {
		if (isReset()) {
			// An async query came in while the loader is stopped. We
			// don't need the result.
			if (data != null) {
			}
		}

		if (isStarted()) {
			super.deliverResult(data);
		}

	}

	 @Override
     protected void onStartLoading() {
	        if (cache != null) {
	            // If we currently have a result available, deliver it
	            // immediately.
	            deliverResult(cache);
	        }
	        else
	        {
	        	forceLoad();
	        }

	        // Start watching for changes in the app data.

	        
	        
	 }
	

}
