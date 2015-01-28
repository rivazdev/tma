package rivazdev.com.tma.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;



import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import rivazdev.com.tma.BuildConfig;
import rivazdev.com.tma.model.Task;

public class DBHandler extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "tma";
    private static final int DATABASE_VERSION = 1;
    private static final AtomicInteger usageCounter = new AtomicInteger(0);
    private static DBHandler helper = null;
    private static final String TAG = DBHandler.class.getSimpleName();


    private static boolean PRE_CACHE = true;

    /*
     * Dao
     */
    private static HashMap<Class<?>, Dao<?, ?>> DaoCache;

    //Table Names go Here
    public static final Class<?>[] TABLES = new Class<?>[] { Task.class };

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Get the helper, possibly constructing it if necessary. For each call to
     * this method, there should be 1 and only 1 call to {@link #close()}.
     */
    public static synchronized DBHandler getHelper(Context context) {
        if (helper == null) {
            helper = new DBHandler(context);
        }
        usageCounter.incrementAndGet();
        return helper;
    }

    /**
     * This is called when the database is first created. Usually you should
     * call createTable statements here to create the tables that will store
     * your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            for (Class<?> table : TABLES) {
                TableUtils.createTable(connectionSource, table);
                if (PRE_CACHE) {
                    getDao(table);
                }
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher
     * version number. This allows you to adjust the various data to match the
     * new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {

        for (int upgrade = oldVersion; upgrade <newVersion; upgrade++) {
            switch (upgrade) {


            }
        }

    }

    @Override
    public void close() {
        if (usageCounter.decrementAndGet() == 0) {
            super.close();
            helper = null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) {
        if (DaoCache == null) {
            DaoCache = new HashMap<Class<?>, Dao<?, ?>>(TABLES.length);
        }
        if (!DaoCache.containsKey(clazz) || DaoCache.get(clazz) != null) {
        	
            try {
                try {
					DaoCache.put(clazz, (Dao<?, ?>) super.getDao(clazz));
				} catch (java.sql.SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } 
            catch (SQLException ex) {
                Log.e(getClass().getSimpleName(), ex.getMessage(), ex);
                DaoCache.remove(clazz);
               
                return null;
            } 
        }
        return (D) DaoCache.get(clazz);
    }

}
