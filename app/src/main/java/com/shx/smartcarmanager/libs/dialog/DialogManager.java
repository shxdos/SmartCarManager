package com.shx.smartcarmanager.libs.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.shx.smartcarmanager.R;
import com.shx.smartcarmanager.commons.LayoutValue;


/**
 * 描述:对话框管理类， 提供普通消息提示框（一个与两个按钮） 可自定义对话框布局
 */
public class DialogManager {

    /**
     * 对话框实体
     */
    private CustomDialog mCustomDialog;
    /**
     * 通信框框实体
     */
    private CustomDialog mProgressDialog;

    private DialogManager() {
    }

    @Nullable
    private static DialogManager mdialogDialogManager = null;

    @Nullable
    public static DialogManager getInstance() {
        if (mdialogDialogManager == null) {
            mdialogDialogManager = new DialogManager();
        }
        return mdialogDialogManager;
    }


    /**
     * 描述:隐藏对话框
     */
    public void dissMissCustomDialog() {
        try {
            if (mCustomDialog != null & mCustomDialog.isShowing()) {
                mCustomDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 描述: 信息消息框（单个按钮）
     *
     * @param context         上下文
     * @param message         要展示的信息
     * @param isCancle
     * @param onclickListener 按钮监听事件 无事件传null
     */
    public void showMessageDialogWithSingleButton(@NonNull Context context, String title,
                                                  String message, View.OnClickListener onclickListener, boolean isCancle) {

        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
        }
        mCustomDialog = new CustomDialog(context, R.style.Theme_Dialog);
        View contentView = initDialogWithSingleButtonView(context, title, message,
                onclickListener);
        mCustomDialog.setCancelable(isCancle);
        mCustomDialog.setContentView(contentView);
        showDialog();
    }

    /**
     * 描述: 信息消息框（单个按钮,按钮名称可自定义）
     *
     * @param context         上下文
     * @param message         要展示的信息
     * @param isCancle
     * @param onclickListener 按钮监听事件 无事件传null
     */
    public void showMessageDialogWithSingleButton(@NonNull Context context, String title,
                                                  String message, String buttonStr, View.OnClickListener onclickListener, boolean isCancle) {

        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
        }
        mCustomDialog = new CustomDialog(context, R.style.Theme_Dialog);
        View contentView = initDialogWithSingleButtonView(context, title, message,buttonStr,
                onclickListener);
        mCustomDialog.setCancelable(isCancle);
        mCustomDialog.setContentView(contentView);
        showDialog();
    }


    /**
     * 描述:初始化提示框 没有提示标题 只有一个确定按钮
     *
     * @param context         上下文
     * @param message         提示信息
     * @param onclickListener 确定按钮监听
     * @return 初始化后的布局
     */
    public View initDialogWithSingleButtonView(Context context, String title, String message,
                                               @Nullable final View.OnClickListener onclickListener) {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.layout_info_message_dialog, null);
        Button confirmBtn = (Button) contentView.findViewById(R.id.btn_confirm_on_dialog);
        Button cancleBtn = (Button) contentView.findViewById(R.id.btn_cancle_on_dialog);
        TextView tvDivider = (TextView) contentView.findViewById(R.id.tvDivider);
        TextView tv_Title = (TextView) contentView.findViewById(R.id.tv_Title);
        tv_Title.setText(title);
        cancleBtn.setVisibility(View.GONE);
        if (tvDivider != null)
            tvDivider.setVisibility(View.GONE);
        View.OnClickListener onClick = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mCustomDialog.dismiss();
                if (onclickListener != null) {
                    onclickListener.onClick(view);
                }
            }
        };
        confirmBtn.setOnClickListener(onClick);
        TextView tvMentionMsg = (TextView) contentView
                .findViewById(R.id.tv_metion_msg);
        tvMentionMsg.setText(message);
        return contentView;

    }

    /**
     * 描述:初始化提示框 没有提示标题 只有一个确定按钮
     *
     * @param context         上下文
     * @param message         提示信息
     * @param onclickListener 确定按钮监听
     * @return 初始化后的布局
     */
    public View initDialogWithSingleButtonView(Context context, String title, String message,
                                               String buttonStr, @Nullable final View.OnClickListener onclickListener) {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.layout_info_message_dialog, null);
        Button confirmBtn = (Button) contentView.findViewById(R.id.btn_confirm_on_dialog);
        Button cancleBtn = (Button) contentView.findViewById(R.id.btn_cancle_on_dialog);
        TextView tvDivider = (TextView) contentView.findViewById(R.id.tvDivider);
        TextView tv_Title = (TextView) contentView.findViewById(R.id.tv_Title);
        tv_Title.setText(title);
        cancleBtn.setVisibility(View.GONE);
        if (tvDivider != null)
            tvDivider.setVisibility(View.GONE);
        View.OnClickListener onClick = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mCustomDialog.dismiss();
                if (onclickListener != null) {
                    onclickListener.onClick(view);
                }
            }
        };
        confirmBtn.setOnClickListener(onClick);
        confirmBtn.setText(buttonStr);
        TextView tvMentionMsg = (TextView) contentView
                .findViewById(R.id.tv_metion_msg);
        tvMentionMsg.setText(message);
        return contentView;

    }
    /**
     * 描述: 普通信息消息框（两个按钮  确定 取消）
     *
     * @param context         上下文
     * @param message         要展示的信息
     * @param onclickListener 按钮监听事件 无事件传null
     */
    public void showMessageDialogWithDoubleButton(@NonNull Context context,
                                                  String message, View.OnClickListener onclickListener, String confirmStr) {

        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
        }
        mCustomDialog = new CustomDialog(context, R.style.Theme_Dialog);
        View contentView = initDialogWithDoubleButtonView(context, message,
                onclickListener, confirmStr);
        mCustomDialog.setCancelable(false);
        mCustomDialog.setContentView(contentView);
        showDialog();
    }

    /**
     * 描述:初始化提示框 没有提示标题 包含两个按钮(确定 取消)
     *
     * @param context         上下文
     * @param message         提示信息
     * @param onclickListener 按钮监听 (确定按钮ID btn_confirm  取消按钮ID btn_cancle)
     * @return 初始化后的布局
     */
    public View initDialogWithDoubleButtonView(Context context, String message,
                                               @Nullable final View.OnClickListener onclickListener, String confirmStr) {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.layout_info_message_dialog, null);
        Button confirmBtn = (Button) contentView.findViewById(R.id.btn_confirm_on_dialog);
        Button cancleBtn = (Button) contentView.findViewById(R.id.btn_cancle_on_dialog);
        View.OnClickListener onClick = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mCustomDialog.dismiss();
                if (onclickListener != null) {
                    onclickListener.onClick(view);
                }
            }
        };
        confirmBtn.setText(confirmStr);
        confirmBtn.setOnClickListener(onClick);
        cancleBtn.setOnClickListener(onClick);
        cancleBtn.setVisibility(View.VISIBLE);
        TextView tvMentionMsg = (TextView) contentView.findViewById(R.id.tv_metion_msg);
        tvMentionMsg.setText(message);
        return contentView;

    }

    /**
     * 描述: 普通信息消息框（两个按钮  确定 取消）
     *  @param context         上下文
     * @param title
     * @param message         要展示的信息
     * @param onclickListener 按钮监听事件 无事件传null
     */
    public void showMessageDialogWithDoubleButton(@NonNull Context context,
                                                  String title, String message, View.OnClickListener onclickListener, String confirmStr, String cancelStr) {

        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
        }
        mCustomDialog = new CustomDialog(context, R.style.Theme_Dialog);
        View contentView = initDialogWithDoubleButtonView(context,title, message,
                onclickListener, confirmStr,cancelStr);
        mCustomDialog.setCancelable(false);
        mCustomDialog.setContentView(contentView);
        showDialog();
    }

    /**
     * 描述:初始化提示框 没有提示标题 包含两个按钮(确定 取消)
     *
     * @param context         上下文
     * @param message         提示信息
     * @param onclickListener 按钮监听 (确定按钮ID btn_confirm  取消按钮ID btn_cancle)
     * @return 初始化后的布局
     */
    public View initDialogWithDoubleButtonView(Context context, String title, String message,
                                               @Nullable final View.OnClickListener onclickListener, String confirmStr, String cancelStr) {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.layout_info_message_dialog, null);
        Button confirmBtn = (Button) contentView.findViewById(R.id.btn_confirm_on_dialog);
        Button cancleBtn = (Button) contentView.findViewById(R.id.btn_cancle_on_dialog);
        TextView tv_Title = (TextView) contentView.findViewById(R.id.tv_Title);
        tv_Title.setText(title);
        View.OnClickListener onClick = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mCustomDialog.dismiss();
                if (onclickListener != null) {
                    onclickListener.onClick(view);
                }
            }
        };
        confirmBtn.setText(confirmStr);
        confirmBtn.setOnClickListener(onClick);
        cancleBtn.setText(cancelStr);
        cancleBtn.setOnClickListener(onClick);
        cancleBtn.setVisibility(View.VISIBLE);
        TextView tvMentionMsg = (TextView) contentView.findViewById(R.id.tv_metion_msg);
        tvMentionMsg.setText(message);
        return contentView;

    }

    /**
     * 描述:展示自定义的对话框
     *
     * @param context     上下文对象
     * @param contentView 对话框视图
     */
    public void showCustomDialog(@NonNull Context context, @NonNull View contentView) {
        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
        }
        mCustomDialog = new CustomDialog(context, R.style.Theme_Dialog);
        mCustomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mCustomDialog.setCancelable(false);
        mCustomDialog.setContentView(contentView);
        showDialog();
    }

    /**
     * 描述:展示自定义的对话框
     *
     * @param context     上下文对象
     * @param contentView 对话框视图
     */
    public void showCustomDialog(@NonNull Context context, @NonNull View contentView, boolean cancelable) {
        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
        }
        mCustomDialog = new CustomDialog(context, R.style.Theme_Dialog);
        mCustomDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mCustomDialog.setCancelable(cancelable);
        mCustomDialog.setContentView(contentView);
        showDialog();

    }


    /**
     * 显示支付输入密码的弹窗
     *
     * @param context
     */
    public void showPayInputPsdDialog(@NonNull Context context, @NonNull View contentView) {

        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
        }
        mCustomDialog = new CustomDialog(context, R.style.Theme_Dialog);
        mCustomDialog.setCancelable(false);
        mCustomDialog.setContentView(contentView);
        WindowManager.LayoutParams lp = mCustomDialog.getWindow()
                .getAttributes();
        lp.width = LayoutValue.SCREEN_WIDTH * 6 / 7;
        lp.gravity = Gravity.CENTER;
        mCustomDialog.getWindow().setAttributes(lp);
        mCustomDialog.show();
        mCustomDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }


    /**
     * 描述: 定义弹出框的宽高，弹出对话框
     */
    public void showDialog() {
        try {
            if (mCustomDialog != null && mCustomDialog.isShowing()) {
                mCustomDialog.dismiss();
            }
            WindowManager windowManager = mCustomDialog.getWindow().getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = mCustomDialog.getWindow()
                    .getAttributes();
            lp.width = display.getWidth(); //设置宽度
            lp.height = display.getHeight();
            mCustomDialog.getWindow().setAttributes(lp);
            lp.gravity = Gravity.CENTER;
            mCustomDialog.getWindow().setAttributes(lp);
            mCustomDialog.show();
            mCustomDialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        } catch (Exception e) {
            //弹出对话框时如果activity已经finish会报异常
            // java.lang.IllegalArgumentException: View=com.android.internal.policy.impl.PhoneWindow$DecorView{4294b1f8 V.E..... R.....I. 0,0-0,0} not attached to window manager
            e.printStackTrace();
        }
    }


    /**
     * 描述:显示通信框
     *
     * @param context 上下文
     */
    public void showProgressDialog(@NonNull Context context) {
        mProgressDialog = createProgressDialog(context);
        mProgressDialog.setCancelable(true);
    }


    /**
     * 描述:显示通信框不可被取消
     *
     * @param context 上下文
     */
    public void showProgressDialogNotCancelbale(@NonNull Context context) {
        mProgressDialog = createProgressDialog(context);
        mProgressDialog.setCancelable(false);
    }

    /**
     * 描述:隐藏通信框
     */
    public void dissMissProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通讯提示框
     */
    @NonNull
    public CustomDialog createProgressDialog(@NonNull final Context con) {
        CustomDialog dlg = new CustomDialog(con, R.style.Theme_Dialog);
        dlg.show();
        dlg.setCancelable(true);
//        Window window = dlg.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.dimAmount = 0f;  //设置背景不变暗
//        window.setAttributes(params);
        LayoutInflater factory = LayoutInflater.from(con);
        // 加载progress_dialog为对话框的布局xml
        View view = factory.inflate(R.layout.progress_dialog, null);
        dlg.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
            }
        });
        dlg.getWindow().setContentView(view);
        WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
        lp.width = LayoutValue.SCREEN_WIDTH * 2 / 4;
        lp.height = LayoutValue.SCREEN_WIDTH / 3;
        dlg.getWindow().setAttributes(lp);
        return dlg;
    }


    public CustomDialog getmCustomDialog() {
        return mCustomDialog;
    }


    public void setmCustomDialog(CustomDialog mCustomDialog) {
        this.mCustomDialog = mCustomDialog;
    }
}
