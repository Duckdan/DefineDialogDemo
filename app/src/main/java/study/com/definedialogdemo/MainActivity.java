package study.com.definedialogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import study.com.definedialogdemo.dialog.SelectAddressDialog;
import study.com.definedialogdemo.dialog.SelectDateDialog;
import study.com.definedialogdemo.dialog.SelectDefineDialog;
import study.com.wheelviewlibrary.listener.SelectInterface;

public class MainActivity extends AppCompatActivity implements SelectInterface {

    private TextView tvResult;
    private SelectAddressDialog dialog;
    private SelectDateDialog dateDialog;
    private SelectDefineDialog defineDialog;
    private String[] defineDatas = {"飞雪连天射白鹿0", "书笑恩侠倚碧鸳1", "黄河之水天上来2", "奔流到海不复回3",
            "白日依山尽4", "欲穷千里目5", "两岸猿声啼不住6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = (TextView) findViewById(R.id.tv_result);
    }

    /**
     * 弹出地址对话框--三级联动的效果
     *
     * @param view
     */
    public void address(View view) {
        dialog = new SelectAddressDialog(MainActivity.this,
                MainActivity.this, SelectAddressDialog.STYLE_THREE, null);
        dialog.showDialog();
    }

    /**
     * 弹出时间对话框
     *
     * @param view
     */
    public void year(View view) {
        dateDialog = new SelectDateDialog(MainActivity.this);
        dateDialog.showDateDialog(MainActivity.this);
    }

    /**
     * 自定义数据的对话框,该对话框支持点击条目
     *
     * @param view
     */
    public void single(View view) {
        defineDialog = new SelectDefineDialog(this, this, defineDatas);
        defineDialog.showDialog();
    }


    @Override
    public void selectedResult(String result) {
        tvResult.setText(result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

}
