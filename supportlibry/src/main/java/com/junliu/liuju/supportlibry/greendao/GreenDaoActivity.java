package com.junliu.liuju.supportlibry.greendao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.junliu.liuju.supportlibry.R;
import com.junliu.liuju.supportlibry.bean.Person;
import com.junliu.liuju.supportlibry.retrofit.BaseActivity;
import com.junliu.liuju.supportlibry.util.GreenDaoManager;

import java.util.List;

/**
 * Created by liuju on 2018/4/10.
 */

public class GreenDaoActivity extends BaseActivity implements View.OnClickListener{
    private Button btnInsert,btnDelete,btnUpdate,btnLoad;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_greendao_activity);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        PersonDao personDao = GreenDaoManager.getInstance(getApplicationContext()).
                getNewDaoSession().getPersonDao();
        Person person = new Person(1,"张三",13);
        switch (v.getId()){
            case R.id.btnInsert:
                personDao.insertOrReplace(person);
                personDao.insertOrReplace(new Person(2,"哈哈哈",98));
                break;

            case R.id.btnDelete:
                break;

            case R.id.btnUpdate:
                person.setId(1);
                person.setName("李四");
                person.setAge(10);
                personDao.update(person);
                break;

            case R.id.btnLoad:
                List<Person> persons = personDao.loadAll();
                Log.i("person",persons.toString());
                break;
        }
    }
}
