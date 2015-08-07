package com.util.luxj;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.util.luxj.model.Student;
import com.util.luxj.widget.MyToast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class SqlTestActivity extends Activity implements View.OnClickListener {
    private Button btn_save, btn_query, btn_delete;
    private MyToast toast;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql);
        initView();
        initData();
        initClick();


    }

    private void initView() {
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        findViewById(R.id.btn_select_album).setOnClickListener(this);
    }

    private void initData() {
        toast = new MyToast(this);
    }

    private void initClick() {
        btn_query.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_album:
                Intent intent = new Intent();
                intent.setClass(SqlTestActivity.this, AlbumGridActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_save:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long start = System.currentTimeMillis();
                        List<Student> studentList = new ArrayList<Student>();
                        for (int i = 0; i < 5000; i++) {
                            Student student = new Student();
                            student.setAge(i);
                            student.setName(getString(R.string.app_name) + "=" + i);
                            student.setDesc(getString(R.string.app_name) + "=" + i);
                            student.setSex(i % 2);
                            studentList.add(student);
                        }
                        DataSupport.saveAll(studentList);
                        toast.showLong("end " + (System.currentTimeMillis() - start));
                    }
                }).start();
                break;

            case R.id.btn_query:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Cursor cursor = null;
                        try {
                            long start_no = System.currentTimeMillis();
                            cursor = Connector.getDatabase().rawQuery("select * from Student order by id limit 5000",
                                    null);
                            List<Student> studentList = new ArrayList<Student>();
                            if (cursor.moveToFirst()) {
                                do {
                                    long id = cursor.getLong(cursor.getColumnIndex("id"));
                                    String name = cursor.getString(cursor.getColumnIndex("name"));
                                    String desc = cursor.getString(cursor.getColumnIndex("desc"));
                                    int age = cursor.getInt(cursor.getColumnIndex("age"));
                                    int sex = cursor.getInt(cursor.getColumnIndex("sex"));
//                                    Log.e(TAG, "id = " + id + " desc = " + desc + " name = " + name + " age = " + age + " sex = " + sex);
                                    Student student = new Student();
                                    student.setAge(age);
                                    student.setName(name);
                                    student.setDesc(desc);
                                    student.setSex(sex);
                                    studentList.add(student);
                                } while (cursor.moveToNext());
                                System.out.println("end  no=========" + (System.currentTimeMillis() - start_no + " size===" + studentList.size()));
                            }
                            long start_fs = System.currentTimeMillis();
                            List<Student> mStudentList = DataSupport.limit(5000).find(Student.class);
                            System.out.println("end  ====" + (System.currentTimeMillis() - start_fs) + " size===" + mStudentList.size());
                            for (Student sb : studentList) {
//                                Log.e(TAG, "id = " + sb.getId() + " desc = " + sb.getDesc() + " name = " + sb.getName() + " age = " + sb.getAge() + " sex = " + sb.getSex());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toast.showShort("query out");
                                }
                            });
                        }
                    }
                }).start();


                break;
            case R.id.btn_delete:
                try {
                    int row = 0;
                    for (int i = 0; i < 10; i++) {
                        row = DataSupport.delete(Student.class, i);
                    }
                    toast.showShort("delete succuss " + row);
                } catch (Exception e) {
                    toast.showShort("delete error");
                }
                break;
        }
    }
}
