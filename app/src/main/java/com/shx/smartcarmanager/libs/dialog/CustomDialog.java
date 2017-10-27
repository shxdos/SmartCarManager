package com.shx.smartcarmanager.libs.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;

import com.shx.smartcarmanager.R;
import com.shx.smartcarmanager.commons.LogGloble;


/**
 * 描述:自定义对话框
 */
public class CustomDialog extends Dialog {
    @Nullable
    private View contentView;
    private Context currentContent;
    private static final String TAG = "CustomDialog";

    public CustomDialog(@NonNull Context context) {
        this(context, R.style.Theme_Dialog);
    }

    public CustomDialog(@NonNull Context context, int theme) {
        super(context, theme);
        this.currentContent = context;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    /**
     * 取得当前dialog的视图
     *
     * @return
     */
    @Nullable
    public View getContentView() {
        return contentView;
    }

    /**
     * dialog.dismiss()之后会回调该方法
     */
    @Override
    protected void onStop() {
        super.onStop();
        contentView = null;
    }

    @Override
    public void dismiss() {
        LogGloble.d(TAG, "custom dialog dismiss!");
        try {
            super.dismiss();
        } catch (BadTokenException e) {
            LogGloble.w(TAG, "custom dialog dismiss error", e);
        }

    }

    public Context getCurrentContent() {
        return currentContent;
    }

    public void setCurrentContent(Context currentContent) {
        this.currentContent = currentContent;
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            LogGloble.w(TAG, "custom dialog show error", e);
        }

    }
}
