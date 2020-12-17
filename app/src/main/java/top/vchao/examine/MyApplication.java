package top.vchao.examine;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lzy.okgo.OkGo;


import top.vchao.examine.utils.LogUtils;

/**
 *
 */

public class MyApplication extends Application {
    //private static DaoSession daoSession;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //必须调用初始化
        OkGo.init(this);
        //配置数据库
        setupDatabase();
    }
    public static MyApplication getInstance() {
        return instance;
    }
    /**
     * 配置数据库
     */
    private void setupDatabase() {
        LogUtils.e("onCreate: 配置数据库 ");
        //创建数据库shop.db"

    }

}
