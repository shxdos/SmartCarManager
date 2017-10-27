package com.shx.smartcarmanager.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shx.smartcarmanager.R;


/**
 * Created by 邵鸿轩 on 14/11/26.
 */
public class ActionBarView {

    private RelativeLayout rl_layout;
    private TextView leftTextView;
    private ImageView leftImageView;
    private TextView rightTextView;
    private ImageView rightImageView;
    private TextView titleView;
    private View lineView;

    public ActionBarView(@Nullable View view) {
        if (view == null) {
            return;
        }
        rl_layout = (RelativeLayout) view.findViewById(R.id.topbar_view);
        leftTextView = (TextView) view.findViewById(R.id.tv_actionBarLeft);
        leftImageView = (ImageView) view.findViewById(R.id.iv_actionBarLeft);
        rightTextView = (TextView) view.findViewById(R.id.tv_actionRight);
        rightImageView = (ImageView) view.findViewById(R.id.iv_actionBarRight);
        titleView = (TextView) view.findViewById(R.id.tv_actionBarTitle);
        lineView =  view.findViewById(R.id.line);
    }

    public TextView getRightTextView() {
        return rightTextView;
    }

    public void setRightTextView(TextView rightTextView) {
        this.rightTextView = rightTextView;
    }

    public void setBackGround(int color) {
        if (rl_layout != null) {
            rl_layout.setBackgroundResource(color);
        }
    }

    /**
     * 左按钮文字
     *
     * @param text
     */
    public void setLeftText(String text) {
        if (leftTextView != null) {
            leftTextView.setText(text);
        }
    }

    /**
     * 左按钮文字
     *
     * @param resId
     */
    public void setLeftText(int resId) {
        if (leftTextView != null) {
            leftTextView.setText(resId);
        }
    }

    /**
     * 左按钮图片
     *
     * @param resId
     */
    public void setLeftImage(int resId) {
        if (leftImageView != null) {
            leftImageView.setImageResource(resId);
        }
    }

    /**
     * 左按钮监听事件
     *
     * @param listener
     */
    public void setLeftTextListener(View.OnClickListener listener) {
        if (leftTextView != null) {
            leftTextView.setOnClickListener(listener);
        }
    }

    /**
     * 左按钮事件
     *
     * @param listener
     */
    public void setLeftImageListener(View.OnClickListener listener) {
        if (leftImageView != null) {
            setLeftImageVisibility(View.VISIBLE);
            leftImageView.setOnClickListener(listener);
        }
    }

    public void setRightImage(int resId) {
        if (rightImageView != null) {
            rightImageView.setVisibility(View.VISIBLE);
            rightImageView.setImageResource(resId);
        }
    }

    /**
     * 右文字
     *
     * @param text
     */
    public void setRightText(String text) {
        if (rightTextView != null) {
            rightTextView.setVisibility(View.VISIBLE);
            rightTextView.setText(text);
        }
    }

    /**
     * 设置右文字的颜色
     */
    public void setRightTextColor(int resId) {
        if (rightTextView != null) {
            rightTextView.setVisibility(View.VISIBLE);
            rightTextView.setTextColor(resId);
        }
    }

    /**
     * 是否显示分割线
     */
    public void isShowLine(boolean visible) {
        if (lineView != null) {
            if (visible) {
                lineView.setVisibility(View.VISIBLE);
            } else {
                lineView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 返回右边当前的文字
     **/
    @NonNull
    public String getRightText() {
        if (rightTextView != null) {
            return rightTextView.getText().toString();
        }
        return "";
    }

    /**
     * 右文字
     *
     * @param resId
     */
    public void setRightText(int resId) {
        if (rightTextView != null) {
            rightTextView.setText(resId);
            rightTextView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 右按钮事件
     *
     * @param listener
     */
    public void setRightTextListener(View.OnClickListener listener) {
        if (rightTextView != null) {
            rightTextView.setOnClickListener(listener);
        }
    }


    public void setRightImageListener(View.OnClickListener listener) {
        if (rightImageView != null) {
            rightImageView.setVisibility(View.VISIBLE);
            rightImageView.setOnClickListener(listener);
        }
    }

    /**
     * 标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    /**
     * 标题
     *
     * @param resId
     */
    public void setTitle(int resId) {
        if (titleView != null) {
            titleView.setText(resId);
        }
    }


    public void setLeftImageVisibility(int visibility) {
        if (leftImageView != null) {
            leftImageView.setVisibility(visibility);
        }
    }

    public void setLeftTextVisibility(int visibility) {
        if (leftTextView != null) {
            leftTextView.setVisibility(visibility);
        }
    }

    public void setRightTextVisibility(int visibility) {
        if (rightTextView != null) {
            rightTextView.setVisibility(visibility);
        }
    }

    public void setRightImageVisibility(int visibility) {
        if (rightImageView != null) {
            rightImageView.setVisibility(visibility);
        }
    }

    public TextView getTitle() {
        return titleView;
    }
}
