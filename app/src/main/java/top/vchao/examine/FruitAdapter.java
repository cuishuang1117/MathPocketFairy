package top.vchao.examine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import top.vchao.examine.bean.QuestBean;

public class FruitAdapter extends ArrayAdapter<QuestBean> {
    private int resourceId;
    public FruitAdapter( Context context,  int textViewResourceId,  List<QuestBean> objects) {
        super(context,  textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        QuestBean questBean=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView fruitName=view.findViewById(R.id.tv_item_title);
        TextView answer=view.findViewById(R.id.tv_item_right);
        TextView myanswer=view.findViewById(R.id.tv_item_wrong);
        fruitName.setText(questBean.getTitle());
        answer.setText("正确答案:"+questBean.getAnswer());
        myanswer.setText("您的答案:"+questBean.getMyanswer());
        return view;
    }




}
