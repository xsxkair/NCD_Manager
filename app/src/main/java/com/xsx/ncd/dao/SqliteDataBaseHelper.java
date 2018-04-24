package com.xsx.ncd.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xsx.ncd.entity.User;

import java.sql.SQLException;

public class SqliteDataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String dataBaseFileName = "xsx.db";

    // 本类的单例实例
    private static SqliteDataBaseHelper instance;

    // 私有的构造方法
    private SqliteDataBaseHelper(Context context) {
        super(context, dataBaseFileName, null, 1);
        Log.d("xsx", "init");
    }

    // 获取本类单例对象的方法
    public static synchronized SqliteDataBaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (SqliteDataBaseHelper.class) {
                if (instance == null) {
                    instance = new SqliteDataBaseHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, User.class);
            Log.d("xsx","create xsx.db success");
        } catch (SQLException e) {
            Log.d("xsx","create xsx.db error: "+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.d("xsx","dropTable");
            TableUtils.dropTable(connectionSource, User.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
