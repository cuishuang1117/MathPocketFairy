package top.vchao.examine.activity;
import android.content.Intent;
import android.view.View;
import butterknife.OnClick;
import top.vchao.examine.R;
public class MainActivity extends BaseActivity {
    @Override
    int getLayoutId() {
        return R.layout.activity_main;
    }
    private Intent intent;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (intent != null){
            // 对于intentService，这一步可能是多余的
            stopService(intent);
        }
    }

    @OnClick({R.id.ll_main_start_answer, R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_main_start_answer:
                //   开始答题  跳转答题设置页面
                Intent intent1 = new Intent(MainActivity.this, TestSettingActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_one:
                // 一年级
                Intent intent3 = new Intent(MainActivity.this, AnswerActivity.class);
               // intent3.putExtra("type", 1);
                startActivity(intent3);
                break;
            case R.id.btn_two:
                //  二年级
                Intent intent4 = new Intent(MainActivity.this, AnswerActivity2.class);
                //intent4.putExtra("type", 2);
                startActivity(intent4);
                break;
            case R.id.btn_three:
                // 三年级
                Intent intent5 = new Intent(MainActivity.this, AnswerActivity3.class);
               // intent5.putExtra("type", 3);
                startActivity(intent5);
                break;
            case R.id.btn_about:
                //  关于
                Intent intent6 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent6);
                break;
        }
    }
}
