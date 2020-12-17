package top.vchao.examine.activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import butterknife.BindView;
import top.vchao.examine.R;
/**
 * @
 */
public class GradeActivity extends BaseActivity {

    @BindView(R.id.tv_grade_score)
    TextView tvGradeScore;
    @BindView(R.id.lv_grade_score_detail)
    ListView lvGradeScoreDetail;

    private ArrayList<CharSequence> titleName;
    private String grade;

    @Override
    int getLayoutId() {
        return R.layout.activity_grade;
    }

    @Override
    void getPreIntent() {
//        接收传递来的数据
        titleName = getIntent().getCharSequenceArrayListExtra("timu");
        grade = getIntent().getStringExtra("grade");
    }

    @Override
    void initData() {
//        设置显示成绩分数
        tvGradeScore.setText("您的成绩是： " + grade);
//        设置适配器
        lvGradeScoreDetail.setAdapter(new MyAdapter());
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
                convertView = LayoutInflater.from(GradeActivity.this).inflate(R.layout.list_item, null);
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
