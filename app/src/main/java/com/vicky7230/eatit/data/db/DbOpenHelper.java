package com.vicky7230.eatit.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.vicky7230.eatit.data.db.model.DaoMaster;
import com.vicky7230.eatit.di.ApplicationContext;
import com.vicky7230.eatit.di.DatabaseInfo;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by agrim on 21/7/17.
 */

public class DbOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Timber.d("DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
    }
}
