package study.com.definedialogdemo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import study.com.definedialogdemo.R;
import study.com.wheelviewlibrary.WheelView;
import study.com.wheelviewlibrary.adapter.ArrayWheelAdapter;
import study.com.wheelviewlibrary.listener.OnWheelClickedListener;
import study.com.wheelviewlibrary.listener.SelectInterface;


public class SelectDefineDialog implements OnClickListener, OnWheelClickedListener {

    /**
     * 所有数据
     */
    protected String[] mDatas;


    /**
     * 当前选中的数据
     */
    protected String mCurrent;

    private WheelView wv;

    private Button mBtnConfirm, mBtnCancel;
    private Activity context;
    private Dialog overdialog;
    private SelectInterface selectAdd;


    public SelectDefineDialog(final Activity context,
                              SelectInterface selectAdd, String[] mDatas) {
        this.selectAdd = selectAdd;
        this.context = context;
        View overdiaView = View.inflate(context, R.layout.dialog_test, null);

        wv = (WheelView) overdiaView.findViewById(R.id.wv);

        mBtnConfirm = (Button) overdiaView.findViewById(R.id.btn_confirm);
        mBtnCancel = (Button) overdiaView.findViewById(R.id.btn_cancel);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        Window window = overdialog.getWindow();
        window.setWindowAnimations(R.style.mystyle); // 添加动画
        overdialog.setContentView(overdiaView);
        overdialog.setCanceledOnTouchOutside(true);
        //设置滚轮滑动监听
        setUpListener();
        if (mDatas != null) {
            this.mDatas = mDatas;
            mCurrent = mDatas[0];
            ArrayWheelAdapter<String> arrayAdapter = new ArrayWheelAdapter<>(context, this.mDatas);
            wv.setViewAdapter(arrayAdapter);
            wv.setCyclic(true);
            // 设置可见条目数量
            wv.setVisibleItems(7);
            wv.addClickingListener(this);

        }


    }


    public void showDialog() {
        if (overdialog != null) {
            if (wv != null) wv.setCurrentItem(0);
            overdialog.show();
            Window win = overdialog.getWindow();
            //弹出的窗口左上右下的距离
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setGravity(Gravity.BOTTOM);
            win.setAttributes(lp);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                selectAdd.selectedResult(mCurrent);
                overdialog.cancel();
                break;
            case R.id.btn_cancel:
                overdialog.cancel();
                break;
            default:
                break;
        }
    }


    /**
     * 添加滑动的监听事件
     */
    private void setUpListener() {
        // 添加onclick事件
        mBtnConfirm.setOnClickListener(this);
        // 添加onclick事件
        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onItemClicked(WheelView wheel, int itemIndex) {
        selectAdd.selectedResult(mDatas[itemIndex]);
        overdialog.cancel();
    }
}
