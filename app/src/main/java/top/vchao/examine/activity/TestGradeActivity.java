package top.vchao.examine.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import top.vchao.examine.FruitAdapter;
import top.vchao.examine.MyIntentService;
import top.vchao.examine.R;
import top.vchao.examine.constants.Config;
import top.vchao.examine.constants.SPkey;
import top.vchao.examine.utils.SPUtils;
import top.vchao.examine.utils.TimeUtils;

public class TestGradeActivity extends BaseActivity {
    @BindView(R.id.tv_test_grade_name)
    TextView tvTestGradeName;
    @BindView(R.id.tv_test_grade_score)
    TextView tvTestGradeScore;
    @BindView(R.id.tv_test_grade_kind)
    TextView tvTestGradeKind;
    @BindView(R.id.tv_test_grade_use_time)
    TextView tvTestGradeUseTime;
    @BindView(R.id.lv_grade_score_detail)
    ListView listView;
    @BindView(R.id.tv_test_grade_num)
    TextView tvTestGradeNum;
    @BindView(R.id.tv_test_grade_end_time)
    TextView tvTestGradeEndTime;
    private ArrayList<CharSequence> titleName;
    private String grade;
    private String time;
    private String kind;
    private String num;
    private String end_time;
    private String username;
    @Override
    int getLayoutId() {
        return R.layout.activity_test_grade;
    }

    @Override
    void getPreIntent() {
//        接收传递来的数据
        titleName = getIntent().getCharSequenceArrayListExtra("timu");
        grade = getIntent().getStringExtra("grade");
        if(Integer.parseInt(grade)>=60)
            submitgood();
        else submitbad();
        time = getIntent().getStringExtra("time");
        kind = getIntent().getStringExtra("kind");
        num = getIntent().getStringExtra("num");
        end_time = TimeUtils.getNowTime();
        username = (String) SPUtils.get(TestGradeActivity.this, SPkey.UserName, "");
    }
    public void submitgood(){
        Intent intent8 = new Intent(TestGradeActivity.this, MyIntentService.class);
        intent8.putExtra("type","submitgood");
        String action = MyIntentService.ACTION_MUSIC;
        // 设置action
        intent8.setAction(action);
        startService(intent8);
    }
    public void submitbad(){
        Intent intent8 = new Intent(TestGradeActivity.this, MyIntentService.class);
        intent8.putExtra("type","submitbad");
        String action = MyIntentService.ACTION_MUSIC;
        // 设置action
        intent8.setAction(action);
        startService(intent8);
    }
    @Override
    void initView() {
//        设置显示成绩分数
        tvTestGradeName.setText(username + " 您的本次得分是：");
        tvTestGradeScore.setText(grade + "分");
        tvTestGradeUseTime.setText("使用时间：" + time);
        tvTestGradeNum.setText("题目数量：" + num);
        tvTestGradeEndTime.setText("交卷时间：" + end_time);
        switch (kind) {
            case Config.TestType_add:
                tvTestGradeKind.setText("试卷类型：加法卷");
                break;
            case Config.TestType_sub:
                tvTestGradeKind.setText("试卷类型：减法卷");
                break;
            case Config.TestType_multiply:
                tvTestGradeKind.setText("试卷类型：乘法卷");
                break;
            case Config.TestType_divide:
                tvTestGradeKind.setText("试卷类型：除法卷");
                break;
            default:
                tvTestGradeKind.setVisibility(View.GONE);
                break;
        }
        FruitAdapter adapter=new FruitAdapter(TestGradeActivity.this,R.layout.list_item,fruitList);
//        设置适配器
        listView.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        fruitList.clear();
    }

    @Override
    void initData() {
//        上传成绩
//
    }

    /**
     * 题目列表适配器
     */
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return titleName != null ? titleName.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return titleName.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            listview优化，复用布局以及id
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(TestGradeActivity.this).inflate(R.layout.list_item, null);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_item_title);
                holder.tvRightAnswer = (TextView) convertView.findViewById(R.id.tv_item_right);
                holder.tvWrongAnswer = (TextView) convertView.findViewById(R.id.tv_item_wrong);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }
        class ViewHolder {
            TextView tvTitle, tvRightAnswer, tvWrongAnswer, tvOptionA,
                    tvOptionB, tvOptionC, tvOptionD;
        }
    }
}
