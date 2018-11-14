package com.junliu.liuju.supportlibry.util;

import android.content.Context;

import com.junliu.liuju.supportlibry.greendao.DaoMaster;
import com.junliu.liuju.supportlibry.greendao.DaoSession;

/**
 * Created by liuju on 2018/4/10.
 */

public class GreenDaoManager {
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private static volatile GreenDaoManager instance;
    public static GreenDaoManager getInstance(Context context){
        if (instance == null){
            synchronized (GreenDaoManager.class){
                if (instance == null)instance = new GreenDaoManager(context);
            }
        }
        return instance;
    }
    private GreenDaoManager(Context context){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context,"person.db",null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }
    public DaoMaster getDaoMaster(){
        return daoMaster;
    }
    public DaoSession getDaoSession(){
        return daoSession;
    }
    public DaoSession getNewDaoSession(){
        daoSession = daoMaster.newSession();
        return daoSession;
    }
}
