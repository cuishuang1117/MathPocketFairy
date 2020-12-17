package top.vchao.examine.activity;
import android.content.Intent;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.OnClick;
import top.vchao.examine.R;
import top.vchao.examine.constants.Config;
public class TestSettingActivity extends BaseActivity {
    @BindView(R.id.rg_test_type)
    RadioGroup rgTestType;
    @BindView(R.id.rg_test_num)
    RadioGroup rgTestNum;
    //    初始化试题类型
    String type = Config.TestType_add;
    //    初始化 题目数目
    int num = 10;
    @Override
    int getLayoutId() {
        return R.layout.activity_test_setting;
    }
    @Override
    void initView() {
        rgTestType.check(R.id.rbtn_add);
        rgTestNum.check(R.id.rbtn_num_10);
    }
    @Override
    void initListener() {
        rgTestType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_multiply:
                        type = Config.TestType_multiply;
                        break;
                    case R.id.rbtn_sub:
                        type = Config.TestType_sub;
                        break;
                    case R.id.rbtn_add:
                        type = Config.TestType_add;
                        break;
                    case R.id.rbtn_divide:
                        type = Config.TestType_divide;
                        break;
                    default:
                        break;
                }
            }
        });
        rgTestNum.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_num_10:
                        num = 10;
                        break;
                    case R.id.rbtn_num_30:
                        num = 30;
                        break;
                }
            }
        });
    }
    @OnClick(R.id.btn_start_test)
    public void onViewClicked() {
        Intent intent = new Intent(TestSettingActivity.this, TestAnswerActivity.class);
        intent.putExtra("kind", type);
        intent.putExtra("num", num + "");
        startActivity(intent);
        finish();
    }
}
