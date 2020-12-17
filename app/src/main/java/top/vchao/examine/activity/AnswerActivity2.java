package top.vchao.examine.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import top.vchao.examine.MyIntentService;
import top.vchao.examine.R;
import top.vchao.examine.bean.QuestBean;
import top.vchao.examine.utils.LogUtils;

/**
 * @ 创建时间: 2020/12/16 on 14:08.
 * @ 描述：二年级答题界面
 * @ 作者: 崔爽 QQ: 1615197850
 */
public class AnswerActivity2 extends BaseActivity implements Chronometer.OnChronometerTickListener {
    @BindView(R.id._chro_exam2)
    Chronometer chronometer2;
    @BindView(R.id.tv_problem2)
    TextView tv_problem2;
    @BindView(R.id._btn_confirm2)
    Button btn_confirm2;
    @BindView(R.id.et_answer2)
    EditText et_answer2;
    private int minute = 0;
    private int second = 0;
    private AlertDialog.Builder builder;
    private ArrayList<String> a;
     private List<QuestBean> messages;
    private ArrayList<String> titleName;
    private String type;
       private String num;
    Random r = new Random();
    int ran1;
    int ran2;
    int kind = r.nextInt(100);
    int count=0;
    int right=0;
    @Override
    int getLayoutId() {
        return R.layout.activity_answer2;
    }
    public void answerright(){
        Intent intent = new Intent(AnswerActivity2.this, MyIntentService.class);
        intent.putExtra("type","right");
        String action = MyIntentService.ACTION_MUSIC;
        // 设置action
        intent.setAction(action);
        startService(intent);
    }
    public void answerwrong(){
        Intent intent = new Intent(AnswerActivity2.this, MyIntentService.class);
        intent.putExtra("type","wrong");
        String action = MyIntentService.ACTION_MUSIC;
        // 设置action
        intent.setAction(action);
        startService(intent);
    }


    @Override
    void initView() {
        if(kind%3==1){
            ran1 = r.nextInt(50);
            ran2 = r.nextInt(50);
            tv_problem2.setText(String.valueOf(ran1)+"+"+String.valueOf(ran2)+"=");
        }
        else if(kind%3==2){
            ran1 = r.nextInt(100);
            ran2 = r.nextInt(ran1);
            tv_problem2.setText(String.valueOf(ran1)+"-"+String.valueOf(ran2)+"=");
        }
        else{
            ran1 = r.nextInt(10);
            ran2 = r.nextInt(10);
            tv_problem2.setText(String.valueOf(ran1)+"×"+String.valueOf(ran2)+"=");
        }
        count++;
        setChronometer();
    }

    /**
     * 设置计时器
     */
    private void setChronometer() {
        chronometer2.setText(nowtime());
        chronometer2.start();
        chronometer2.setOnChronometerTickListener(this);
    }

    /**
     * 计时器规则
     *
     * @param chronometer
     */
    @Override
    public void onChronometerTick(Chronometer chronometer) {
        second++;
        if (second == 59) {
            minute++;
            second = 00;
        }
    }

    /**
     * 现在时间
     *
     * @return
     */
    private String nowtime() {
        if (second < 10) {
            return (minute + ":0" + second);
        } else {
            return (minute + ":" + second);
        }
    }


    @OnClick({R.id._btn_confirm2, R.id._btn_submit2, R.id._btn_next2})
    public void onViewClicked(View view) {
        String inputtext=et_answer2.getText().toString();
        QuestBean temp;
        switch (view.getId()) {
            case R.id._btn_confirm2:
                if(inputtext.equals(""))
                    Toast.makeText(AnswerActivity2.this,"请先输入结果再点确认！",Toast.LENGTH_SHORT).show();
                else {
                if(kind%3==1){
                    if (Integer.parseInt(inputtext) == ran1 + ran2) {
                        Toast.makeText(AnswerActivity2.this, "正确", Toast.LENGTH_SHORT).show();
                        answerright();
                    } else {
                        Toast.makeText(AnswerActivity2.this, "错误", Toast.LENGTH_SHORT).show();
                        answerwrong();
                    }
                }
                else if(kind%3==2){
                    if (Integer.parseInt(inputtext) == ran1 - ran2)
                    {
                        Toast.makeText(AnswerActivity2.this, "正确", Toast.LENGTH_SHORT).show();
                        answerright();
                    }
                    else {
                        Toast.makeText(AnswerActivity2.this, "错误", Toast.LENGTH_SHORT).show();
                        answerwrong();
                    }
                }
                else {
                    if (Integer.parseInt(inputtext) == ran1 * ran2) {
                        Toast.makeText(AnswerActivity2.this, "正确", Toast.LENGTH_SHORT).show();
                        answerright();
                    } else {
                        Toast.makeText(AnswerActivity2.this, "错误", Toast.LENGTH_SHORT).show();
                        answerwrong();
                    }
                }
                }
                break;
//            点击提交按钮
            case R.id._btn_submit2:
                if(!inputtext.equals("")) {
                    if (kind % 3 == 1) {
                        if (Integer.parseInt(inputtext) == ran1 + ran2)
                            right++;
                        else {
                            temp = new QuestBean(String.valueOf(ran1) + "+" + String.valueOf(ran2) + "=", String.valueOf(ran1 + ran2), inputtext);
                            fruitList.add(temp);
                        }
                    } else if (kind % 3 == 2) {
                        if (Integer.parseInt(inputtext) == ran1 - ran2)
                            right++;
                        else {
                            temp = new QuestBean(String.valueOf(ran1) + "-" + String.valueOf(ran2) + "=", String.valueOf(ran1 - ran2), inputtext);
                            fruitList.add(temp);
                        }
                    }
                    else if(kind%3==0){
                        if (Integer.parseInt(inputtext) == ran1 * ran2)
                            right++;
                        else {
                            temp = new QuestBean(String.valueOf(ran1) + "×" + String.valueOf(ran2) + "=", String.valueOf(ran1 * ran2), inputtext);
                            fruitList.add(temp);
                        }
                    }
                }
                else
                {
                    if(kind%3==1){
                        temp = new QuestBean(String.valueOf(ran1) + "+" + String.valueOf(ran2) + "=", String.valueOf(ran1 + ran2), "无");
                        fruitList.add(temp);
                    }
                    else if(kind%3==2){
                        temp = new QuestBean(String.valueOf(ran1) + "-" + String.valueOf(ran2) + "=", String.valueOf(ran1 - ran2), "无");
                        fruitList.add(temp);
                    }
                    else if(kind%3==0){
                        temp = new QuestBean(String.valueOf(ran1) + "×" + String.valueOf(ran2) + "=", String.valueOf(ran1 * ran2), "无");
                        fruitList.add(temp);
                    }
                }
//              初始化并展示提交对话框
                initAlertDialog();
                builder.show();
                break;
//            点击下一题按钮
            case R.id._btn_next2:
                if(!inputtext.equals("")) {
                    if (kind % 3 == 1) {
                        if (Integer.parseInt(inputtext) == ran1 + ran2)
                            right++;
                        else {
                            temp = new QuestBean(String.valueOf(ran1) + "+" + String.valueOf(ran2) + "=", String.valueOf(ran1 + ran2), inputtext);
                            fruitList.add(temp);
                        }
                    } else if (kind % 3 == 2) {
                        if (Integer.parseInt(inputtext) == ran1 - ran2)
                            right++;
                        else {
                            temp = new QuestBean(String.valueOf(ran1) + "-" + String.valueOf(ran2) + "=", String.valueOf(ran1 - ran2), inputtext);
                            fruitList.add(temp);
                        }
                    }
                    else if(kind%3==0){
                        if (Integer.parseInt(inputtext) == ran1 * ran2)
                            right++;
                        else {
                            temp = new QuestBean(String.valueOf(ran1) + "×" + String.valueOf(ran2) + "=", String.valueOf(ran1 * ran2), inputtext);
                            fruitList.add(temp);
                        }
                    }
                }
                else
                {
                    if(kind%3==1){
                        temp = new QuestBean(String.valueOf(ran1) + "+" + String.valueOf(ran2) + "=", String.valueOf(ran1 + ran2), "无");
                        fruitList.add(temp);
                    }
                    else if(kind%3==2){
                        temp = new QuestBean(String.valueOf(ran1) + "-" + String.valueOf(ran2) + "=", String.valueOf(ran1 - ran2), "无");
                        fruitList.add(temp);
                    }
                    else if(kind%3==0){
                        temp = new QuestBean(String.valueOf(ran1) + "×" + String.valueOf(ran2) + "=", String.valueOf(ran1 * ran2), "无");
                        fruitList.add(temp);
                    }
                }
                if(!inputtext.equals(""))
                    et_answer2.setText("");
                kind = r.nextInt(100);
                if(kind%3==1){
                    ran1 = r.nextInt(50);
                    ran2 = r.nextInt(50);
                    tv_problem2.setText(String.valueOf(ran1)+"+"+String.valueOf(ran2)+"=");
                }
                else if(kind%3==2){
                    ran1 = r.nextInt(100);
                    ran2 = r.nextInt(ran1);
                    tv_problem2.setText(String.valueOf(ran1)+"-"+String.valueOf(ran2)+"=");
                }
                else{
                    ran1 = r.nextInt(10);
                    ran2 = r.nextInt(10);
                    tv_problem2.setText(String.valueOf(ran1)+"×"+String.valueOf(ran2)+"=");
                }
                count++;
        }
    }
    // 弹出是否确认交卷的对话框
    private void initAlertDialog() {
        //新建对话框
        builder = new AlertDialog.Builder(AnswerActivity2.this);
        builder.setTitle("提示");
        builder.setMessage("是否确定交卷?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                计算分数
                int grade = 0;
//                判断题
                grade=right*100/count;
                LogUtils.e("经过计算后，该试卷得分为" + grade);
//                传递分数
                Intent intent = new Intent(AnswerActivity2.this, TestGradeActivity.class);
                intent.putExtra("grade", "" + grade);
//                传递题目列表
                intent.putStringArrayListExtra("timu", titleName);
                intent.putExtra("time", nowtime());
                intent.putExtra("kind", "二年级");
                intent.putExtra("num", String.valueOf(count));
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
    }
}
