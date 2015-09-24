package com.daxueoo.shopnc.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daxueoo.shopnc.R;
import com.daxueoo.shopnc.api.FinishTask;
import com.daxueoo.shopnc.bean.Task;

import com.daxueoo.shopnc.network.ServiceGenerator;
import com.daxueoo.shopnc.utils.ConstUtils;
import com.daxueoo.shopnc.utils.SharedPreferencesUtils;
import com.daxueoo.shopnc.utils.TitleBuilder;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 15-9-9.
 */
public class TaskDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "CreateThemeActivity";
    private TextView et_title;
    private TextView et_content;
    private Task task;

//    private Button btn_finish;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                this.finish();
                break;
//            case R.id.btn_finish:
//                if (task.getArticleState().equals("1")) {
//                    FinishTask finishTask = ServiceGenerator.createService(FinishTask.class, ConstUtils.BASE_URL);
//                    finishTask.finishTask((String) SharedPreferencesUtils.getParam(TaskDetailActivity.this, "key", "key"), task.getArticleId(), new Callback<JSONObject>() {
//                        @Override
//                        public void success(JSONObject jsonObject, Response response) {
//                            Log.e(jsonObject.toString(), "sfs");
//                            Toast.makeText(TaskDetailActivity.this, "已完成此任务", Toast.LENGTH_SHORT);
//                            finish();
//                        }
//
//                        @Override
//                        public void failure(RetrofitError error) {
//
//                        }
//                    });
//
//                } else if (task.getArticleState().equals("3")) {
//                    Toast.makeText(TaskDetailActivity.this, "已经完成此任务", Toast.LENGTH_SHORT);
//                } else {
//                    btn_finish.setBackgroundColor(Color.GREEN);
//                }
//                break;
            case R.id.normal_plus:
                Intent intent = new Intent();
                intent.putExtra("task_edit", task);
                intent.setClass(TaskDetailActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, 2);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        new TitleBuilder(this).setTitleText("任务详情").setLeftImage(R.drawable.navigationbar_back_sel).setLeftOnClickListener(this);
        task = (Task) getIntent().getSerializableExtra("task");
        initView();
        initFab();
        initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        et_title.setText(data.getStringExtra("task_title"));
        et_content.setText(data.getStringExtra("task_content"));
//        initBtn();
    }

//    private void initBtn(){
//        if (task.getArticleState().equals("1")) {
//            btn_finish.setText("未完成");
//            btn_finish.setBackgroundColor(Color.RED);
//        } else if (task.getArticleState().equals("3")) {
//            btn_finish.setText("已完成");
//            btn_finish.setBackgroundColor(Color.GREEN);
//        } else {
//            btn_finish.setText("回收站");
//            btn_finish.setBackgroundColor(Color.GREEN);
//        }
//    }

    private void initView() {
        et_title = (TextView) findViewById(R.id.et_title);
        et_content = (TextView) findViewById(R.id.et_content);
//        btn_finish = (Button) findViewById(R.id.btn_finish);
//        btn_finish.setOnClickListener(this);
//        initBtn();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        et_title.setText(task.getArticleTitle());
        et_content.setText(task.getArticleContent());
    }

    /**
     * 初始化浮动按钮
     */
    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.normal_plus);
        fab.setOnClickListener(this);
    }
}
