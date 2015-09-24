package com.daxueoo.shopnc.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.api.AddTask;
import com.daxueoo.shopnc.api.EditTask;
import com.daxueoo.shopnc.bean.Task;
import com.daxueoo.shopnc.network.ServiceGenerator;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.DateUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.daxueoo.shopnc.utils.ToastUtils;
import com.melnykov.fab.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 15-9-9.
 */
public class AddTaskActivity extends BaseActivity implements View.OnClickListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private static final String TAG = "CreateThemeActivity";
    private EditText et_title;
    private EditText et_content;
    private Task task;
    private boolean type = false;

    private String taskDate;

    private TextView tv_pick_date, tv_task_date, tv_task_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        new TitleBuilder(this).setTitleText("添加一条待办").setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);

        if (getIntent().hasExtra("task_edit")) {
            type = true;
            task = (Task) getIntent().getSerializableExtra("task_edit");
        }
        initView();

        initFab();
        initData();
    }

    private void initView() {
        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        tv_pick_date = (TextView) findViewById(R.id.tv_task_date);
        tv_task_time = (TextView) findViewById(R.id.tv_task_time);
        tv_pick_date = (TextView) findViewById(R.id.tv_pick_date);

        tv_pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDatePicker();
            }
        });

    }

    private void showDialogDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                AddTaskActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(true);
        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.setAccentColor(Color.parseColor("#4CAF9D"));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void showDialogTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                AddTaskActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.setThemeDark(true);
        tpd.vibrate(true);
        tpd.dismissOnPause(true);
        tpd.setAccentColor(Color.parseColor("#4CAF9D"));
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    private void initData() {
        if (type) {
            et_title.setText(task.getArticleTitle());
            et_content.setText(task.getArticleContent());
        }
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.normal_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  发送数据
                if (type) {
                    EditTask editTask = ServiceGenerator.createService(EditTask.class, ConstUtils.BASE_URL);
                    editTask.EditTask(SharedPreferencesUtils.getParam(AddTaskActivity.this, "key", "key").toString(), et_title.getText().toString(), et_content.getText().toString(), "", "", task.getArticleId(), new Callback<JSONObject>() {
                        @Override
                        public void success(JSONObject jsonObject, Response response) {
                            Intent intent = new Intent();
                            intent.putExtra("task_title", et_title.getText().toString());
                            intent.putExtra("task_content", et_content.getText().toString());

                            setResult(RESULT_OK, intent);

                            AddTaskActivity.this.finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e("sfdf", error.toString());
                        }
                    });
                } else {
                    if (taskDate.equals("")) {
                        ToastUtils.showToast(AddTaskActivity.this,"请选择日期", Toast.LENGTH_SHORT);
                    } else {
                        Log.e("TADKDATE", DateUtils.date2TimeStamp(taskDate, "yyyy-MM-dd HH:mm:ss"));
                        AddTask addTask = ServiceGenerator.createService(AddTask.class, ConstUtils.BASE_URL);
                        addTask.AddTask(SharedPreferencesUtils.getParam(AddTaskActivity.this, "key", "key").toString(), et_title.getText().toString(), et_content.getText().toString(), "", DateUtils.date2TimeStamp(taskDate, "yyyy-MM-dd HH:mm:ss"), new Callback<JSONObject>() {
                            @Override
                            public void success(JSONObject jsonObject, Response response) {
                                AddTaskActivity.this.finish();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.e("sfdf", error.toString());
                            }
                        });
                    }

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                this.finish();
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        String date = "日期: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        tv_pick_date.setText(date);
        showDialogTimePicker();
        taskDate = year + "-" + monthOfYear + "-" + dayOfMonth + " ";
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = "时间: " + hourString + ":" + minuteString;
        tv_task_time.setText(time);
        taskDate += hourOfDay + ":" + minute + ":00";
    }
}
