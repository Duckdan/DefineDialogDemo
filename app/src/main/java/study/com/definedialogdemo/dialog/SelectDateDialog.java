package study.com.definedialogdemo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import study.com.definedialogdemo.R;
import study.com.wheelviewlibrary.WheelView;
import study.com.wheelviewlibrary.adapter.NumericWheelAdapter;
import study.com.wheelviewlibrary.listener.OnWheelChangedListener;
import study.com.wheelviewlibrary.listener.SelectInterface;


public class SelectDateDialog implements OnWheelChangedListener {


    public static final int START_YEAR = 1900;
    public static final int END_YEAR = 2100;


    private WheelView year;
    private WheelView month;
    private WheelView day;


    private Activity context;
    private SelectInterface selectAdd;


    public SelectDateDialog(SelectInterface selectAdd) {
        this.selectAdd = selectAdd;
    }


    public void showDateDialog(final Context context) {
        this.context = (Activity) context;
        final AlertDialog dialog = new AlertDialog.Builder(context, R.style.dialog_lhp)
                .create();
        dialog.show();

        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_select_address);
        // 设置宽高
        window.getDecorView().setPadding(0, 0, 0, 0);

        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.mystyle);
        window.setGravity(Gravity.BOTTOM);

        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        int curDate = c.get(Calendar.DATE);
        year = (WheelView) window.findViewById(R.id.id_province);
        initYear(context);
        month = (WheelView) window.findViewById(R.id.id_city);
        initMonth(context);
        day = (WheelView) window.findViewById(R.id.id_district);
        initDay(curYear, curMonth, context);

        year.setCurrentItem(curYear - START_YEAR);
        month.setCurrentItem(curMonth - 1);
        day.setCurrentItem(curDate - 1);

        year.setVisibleItems(9);
        month.setVisibleItems(9);
        day.setVisibleItems(9);

        //设置滚轮滑动监听
        setDateUpListener();

        // 设置监听
        Button ok = (Button) window.findViewById(R.id.btn_confirm);
        Button cancel = (Button) window.findViewById(R.id.btn_cancel);
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = String.format(Locale.CHINA,
                        "%4d-%2d-%2d", year.getCurrentItem() + START_YEAR,//1900
                        month.getCurrentItem() + 1, day.getCurrentItem() + 1);
                selectAdd.selectedResult(str);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    final Date date = dateFormat.parse(str);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dialog.cancel();
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        LinearLayout cancelLayout = (LinearLayout) window.findViewById(R.id.view_none);
        cancelLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dialog.cancel();
                return false;
            }
        });



    }

    private void setDateUpListener() {
        year.addChangingListener(this);
        month.addChangingListener(this);
        year.addChangingListener(this);
    }


    /**
     * 初始化年
     */
    private void initYear(Context context) {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context,
                START_YEAR, END_YEAR);
        numericWheelAdapter.setLabel(" 年");
        year.setViewAdapter(numericWheelAdapter);
        year.setCyclic(true);
    }

    /**
     * 初始化月
     */
    private void initMonth(Context context) {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context, 1, 12, "%02d");
        numericWheelAdapter.setLabel(" 月");
        month.setViewAdapter(numericWheelAdapter);
        month.setCyclic(true);
    }

    /**
     * 初始化天
     */
    private void initDay(int arg1, int arg2, Context context) {
        //设置适配器
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context, 1, getDay(arg1, arg2), "%02d");
        //创建标签
        numericWheelAdapter.setLabel(" 日");
        day.setViewAdapter(numericWheelAdapter);
        day.setCyclic(true);
    }

    private int getDay(int year, int month) {
        int day;
        boolean flag;
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            flag = true;
        } else {
            flag = false;
        }

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }


    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == year || wheel == month) {
            int yearNum = year.getCurrentItem() + START_YEAR;
            int monthNum = month.getCurrentItem() + 1;
            //如果当期滑动的是年并且当前月份不等于2时结束监听避免重复创建对象
            if (wheel == year && monthNum != 2) {
                return;
            }
            initDay(yearNum, monthNum, context);
        }
    }
}
