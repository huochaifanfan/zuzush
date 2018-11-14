package com.junliu.liuju.supportlibry.keyboard;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liuju on 2018/4/2.
 */

public class KeyboardEditText extends AppCompatEditText {
    private Context context;
    private KeyboardView keyboardView;
    private ViewGroup viewGroup;
    private boolean changeLetter;
    /**是否为大写字母*/
    private boolean isCapital;
    private OnKeyboardStateChangeListener onKeyboardStateChangeListener;
    private View keyboardNumber;
    private View keyboardLetter;

    public KeyboardEditText(Context context) {
        this(context,null);
    }

    public KeyboardEditText(Context context, AttributeSet attrs) {
        this(context, attrs ,0);
    }

    public KeyboardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initEditView();
    }

    private void initEditView() {

    }

    public interface OnKeyboardStateChangeListener{
        void show();
        void hide();
    }
}
