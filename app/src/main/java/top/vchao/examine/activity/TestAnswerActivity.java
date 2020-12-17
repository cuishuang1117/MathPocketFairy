package top.vchao.examine.activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;
import top.vchao.examine.MyIntentService;
import top.vchao.examine.R;
import top.vchao.examine.bean.QuestBean;
import top.vchao.examine.utils.LogUtils;
/**
 * 试卷答题 页面
 */
public class TestAnswerActivity<num, kind> extends BaseActivity implements Chronometer.OnChronometerTickListener {
    @BindView(R.id.tv_problem)
    TextView tv_problem;
    @BindView(R.id._chro_exam)
    Chronometer chronometer;
    @BindView(R.id._btn_confirm)
    Button btn_confirm;
    @BindView(R.id.et_answer)
    EditText et_answer;
    @BindView(R.id.et_remain)
    EditText et_remain;
    @BindView(R.id.pb_test)
    ProgressBar pb_test;
    private int minute = 0;
    private int second = 0;
    private AlertDialog.Builder builder;
    private ArrayList<String> titleName;
    private String kind;
    private String num;
    Random r = new Random();
    int ran1 = r.nextInt(120);
    int ran2 = r.nextInt(ran1);
    int count=0;
    int right=0;
    @Override
    int getLayoutId() {
        return R.layout.activity_test_answer;
    }
    @Override
    void getPreIntent() {
        //获取传递来的变量
        kind = getIntent().getStringExtra("kind");
        num = getIntent().getStringExtra("num");
    }
    @Override
    void initView() {
        //vp_answer.setOnPageChangeListener(new MyOnPageChangeListener());
        switch (kind){
            case "add":
                tv_problem.setText(String.valueOf(ran1)+"+"+String.valueOf(ran2)+"=");
                break;
            case "sub":
                tv_problem.setText(String.valueOf(ran1)+"-"+String.valueOf(ran2)+"=");
                break;
            case "multiply":
                tv_problem.setText(String.valueOf(ran1)+"×"+String.valueOf(ran2)+"=");
                break;
            case "divide":
                while(ran2==0)
                    ran2=r.nextInt(ran1);
                tv_problem.setText(String.valueOf(ran1)+"÷"+String.valueOf(ran2)+"=");
                et_remain.setVisibility(View.VISIBLE);
                break;
        }
        count++;
        pb_test.setMax(Integer.parseInt(num));
        pb_test.setProgress(count*10/Integer.parseInt(num));
        setChronometer();
    }
    /**
     * 设置计时器
     */
    private void setChronometer() {
        chronometer.setText(nowtime());
        chronometer.start();
        chronometer.setOnChronometerTickListener(this);
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
    @OnClick({R.id._btn_confirm, R.id._btn_submit, R.id._btn_next})
    public void onViewClicked(View view) {
        String inputtext=et_answer.getText().toString();
        String inputtext2=et_remain.getText().toString();
        QuestBean temp;
        switch (view.getId()) {
            case R.id._btn_confirm:
                if(inputtext.equals(""))
                    Toast.makeText(TestAnswerActivity.this,"请先输入结果再点确认！",Toast.LENGTH_SHORT).show();
                else {
                    if (kind.equals("add")) {
                        if (Integer.parseInt(inputtext) == ran1 + ran2) {
                            Toast.makeText(TestAnswerActivity.this, "正确", Toast.LENGTH_SHORT).show();
                            answerright();
                        } else {
                            Toast.makeText(TestAnswerActivity.this, "错误", Toast.LENGTH_SHORT).show();
                            answerwrong();
                        }
                    } else if (kind.equals("sub")) {
                        if (Integer.parseInt(inputtext) == ran1 - ran2) {
                            Toast.makeText(TestAnswerActivity.this, "正确", Toast.LENGTH_SHORT).show();
                            answerright();
                        } else {
                            Toast.makeText(TestAnswerActivity.this, "错误", Toast.LENGTH_SHORT).show();
                            answerwrong();
                        }
                    } else if (kind.equals("multiply")) {
                        if (Integer.parseInt(inputtext) == ran1 * ran2) {
                            Toast.makeText(TestAnswerActivity.this, "正确", Toast.LENGTH_SHORT).show();
                            answerright();
                        } else {
                            Toast.makeText(TestAnswerActivity.this, "错误", Toast.LENGTH_SHORT).show();
                            answerwrong();
                        }
                    } else {
                        if(inputtext2.equals(""))
                            Toast.makeText(TestAnswerActivity.this,"请先输入结果再点确认！",Toast.LENGTH_SHORT).show();
                        else if ((Integer.parseInt(inputtext) == ran1 / ran2) && ((Integer.parseInt(inputtext2) == ran1 % ran2))) {
                            Toast.makeText(TestAnswerActivity.this, "正确", Toast.LENGTH_SHORT).show();
                            answerright();
                        }
                        else {
                            Toast.makeText(TestAnswerActivity.this, "错误", Toast.LENGTH_SHORT).show();
                            answerwrong();
                        }
                    }
                }
                break;
//            点击提交按钮
            case R.id._btn_submit:
//                否则初始化并展示提交对话框
                if(!inputtext.equals("")){
                    if(kind.equals("add")){
                        if(Integer.parseInt(inputtext) == ran1 + ran2)
                            right++;
                        else{
                            temp=new QuestBean(String.valueOf(ran1)+"+"+String.valueOf(ran2)+"=",String.valueOf(ran1+ran2),inputtext);
                            fruitList.add(temp);
                        }
                    }
                    else if(kind.equals("sub")){
                        if(Integer.parseInt(inputtext) == ran1 - ran2)
                            right++;
                        else{
                            temp=new QuestBean(String.valueOf(ran1)+"-"+String.valueOf(ran2)+"=",String.valueOf(ran1-ran2),inputtext);
                            fruitList.add(temp);
                        }
                    }
                    else if(kind.equals("multiply")){
                        if(Integer.parseInt(inputtext) == ran1 * ran2)
                            right++;
                        else{
                            temp=new QuestBean(String.valueOf(ran1)+"×"+String.valueOf(ran2)+"=",String.valueOf(ran1*ran2),inputtext);
                            fruitList.add(temp);
                        }
                    }
                    else if(kind.equals("divide")){
                        if((Integer.parseInt(inputtext) == ran1 / ran2)&&(Integer.parseInt(inputtext2) == ran1 % ran2))
                            right++;
                        else{
                            temp=new QuestBean(String.valueOf(ran1)+"÷"+String.valueOf(ran2)+"=","商"+String.valueOf(ran1/ran2)+" 余"+String.valueOf(ran1%ran2),"商"+inputtext+" 余"+inputtext2);
                            fruitList.add(temp);
                        }
                    }
                }
                else{
                    if(kind.equals("add")){
                        temp = new QuestBean(String.valueOf(ran1) + "+" + String.valueOf(ran2) + "=", String.valueOf(ran1 + ran2), "无");
                        fruitList.add(temp);
                    }
                    else if(kind.equals("sub")){
                        temp = new QuestBean(String.valueOf(ran1) + "-" + String.valueOf(ran2) + "=", String.valueOf(ran1 - ran2), "无");
                        fruitList.add(temp);
                    }
                    else if(kind.equals("multiply")){
                        temp = new QuestBean(String.valueOf(ran1) + "*" + String.valueOf(ran2) + "=", String.valueOf(ran1 * ran2), "无");
                        fruitList.add(temp);
                    }
                    else if(kind.equals("divide")){
                        temp = new QuestBean(String.valueOf(ran1) + "÷" + String.valueOf(ran2) + "=", "商"+String.valueOf(ran1/ran2)+" 余"+String.valueOf(ran1%ran2), "无");
                        fruitList.add(temp);
                    }
                }
                initAlertDialog();
                builder.show();
                break;
//            点击下一题按钮
            case R.id._btn_next:
                if(count==Integer.parseInt(num)){
                    Toast.makeText(TestAnswerActivity.this,"已经是最后一题啦",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(!inputtext.equals("")){
                    if(kind.equals("add")){
                        if(Integer.parseInt(inputtext) == ran1 + ran2)
                            right++;
                        else{
                            temp=new QuestBean(String.valueOf(ran1)+"+"+String.valueOf(ran2)+"=",String.valueOf(ran1+ran2),inputtext);
                            fruitList.add(temp);
                        }
                    }
                    else if(kind.equals("sub")){
                        if(Integer.parseInt(inputtext) == ran1 - ran2)
                            right++;
                        else{
                            temp=new QuestBean(String.valueOf(ran1)+"-"+String.valueOf(ran2)+"=",String.valueOf(ran1-ran2),inputtext);
                            fruitList.add(temp);
                        }
                    }
                    else if(kind.equals("multiply")){
                        if(Integer.parseInt(inputtext) == ran1 * ran2)
                            right++;
                        else{
                            temp=new QuestBean(String.valueOf(ran1)+"×"+String.valueOf(ran2)+"=",String.valueOf(ran1*ran2),inputtext);
                            fruitList.add(temp);
                        }
                    }
                    else if(kind.equals("divide")){
                        if((Integer.parseInt(inputtext) == ran1 / ran2)&&(Integer.parseInt(inputtext2) == ran1 % ran2))
                            right++;
                        else{
                            temp=new QuestBean(String.valueOf(ran1)+"÷"+String.valueOf(ran2)+"=","商"+String.valueOf(ran1/ran2)+" 余"+String.valueOf(ran1%ran2),"商"+inputtext+" 余"+inputtext2);
                            fruitList.add(temp);
                        }
                    }
                }
                else{
                    if(kind.equals("add")){
                        temp = new QuestBean(String.valueOf(ran1) + "+" + String.valueOf(ran2) + "=", String.valueOf(ran1 + ran2), "无");
                        fruitList.add(temp);
                    }
                    else if(kind.equals("sub")){
                        temp = new QuestBean(String.valueOf(ran1) + "-" + String.valueOf(ran2) + "=", String.valueOf(ran1 - ran2), "无");
                        fruitList.add(temp);
                    }
                    else if(kind.equals("multiply")){
                        temp = new QuestBean(String.valueOf(ran1) + "*" + String.valueOf(ran2) + "=", String.valueOf(ran1 * ran2), "无");
                        fruitList.add(temp);
                    }
                    else if(kind.equals("divide")){
                        temp = new QuestBean(String.valueOf(ran1) + "÷" + String.valueOf(ran2) + "=", "商"+String.valueOf(ran1/ran2)+" 余"+String.valueOf(ran1%ran2), "无");
                        fruitList.add(temp);
                    }
                }
                if(!inputtext.equals(""))
                    et_answer.setText("");
                if(!inputtext2.equals(""))
                    et_remain.setText("");
                ran1 = r.nextInt(100);
                ran2 = r.nextInt(ran1);
               if(kind.equals("add"))
                   tv_problem.setText(String.valueOf(ran1)+"+"+String.valueOf(ran2)+"=");
               else if(kind.equals("sub"))
                   tv_problem.setText(String.valueOf(ran1)+"-"+String.valueOf(ran2)+"=");
               else if(kind.equals("multiply"))
                   tv_problem.setText(String.valueOf(ran1)+"×"+String.valueOf(ran2)+"=");
               else
               {
                   while(ran2==0)
                       ran2=r.nextInt(ran1);
                   tv_problem.setText(String.valueOf(ran1)+"÷"+String.valueOf(ran2)+"=");
               }
               count++;
               pb_test.setProgress(count);
               break;
        }
    }
    public void answerright(){
        Intent intent = new Intent(TestAnswerActivity.this, MyIntentService.class);
        intent.putExtra("type","right");
        String action = MyIntentService.ACTION_MUSIC;
        // 设置action
        intent.setAction(action);
        startService(intent);
    }
    public void answerwrong(){
        Intent intent = new Intent(TestAnswerActivity.this, MyIntentService.class);
        intent.putExtra("type","wrong");
        String action = MyIntentService.ACTION_MUSIC;
        // 设置action
        intent.setAction(action);
        startService(intent);
    }
    // 弹出是否确认交卷的对话框
    private void initAlertDialog() {
        //新建对话框
        builder = new AlertDialog.Builder(TestAnswerActivity.this);
        builder.setTitle("提示");
        builder.setMessage("是否确定交卷?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                计算分数
                int grade = 0;
//                判断题
                grade=right*100/Integer.parseInt(num);
                LogUtils.e("经过计算后，该试卷得分为" + grade);
//                传递分数
                Intent intent = new Intent(TestAnswerActivity.this, TestGradeActivity.class);
                intent.putExtra("grade", "" + grade);
//                传递题目列表
                intent.putStringArrayListExtra("timu", titleName);
                intent.putExtra("time", nowtime());
                intent.putExtra("kind", kind);
                intent.putExtra("num", num);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
    }
}
