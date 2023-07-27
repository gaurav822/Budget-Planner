package com.gaurav.budgetplanner.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.gaurav.budgetplanner.R;
import com.gaurav.budgetplanner.Utils.Animation.CustomRippleEffect;


public class CustomPinKeyboard extends ConstraintLayout implements View.OnClickListener,
        View.OnLongClickListener {

    private Listener listener;

    private CustomRippleEffect customRippleEffect;

    // constructors
    public CustomPinKeyboard(Context context) {
        super(context, null, 0);
        init(context, null);
    }

    public CustomPinKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs);
    }

    public CustomPinKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // keyboard keys (buttons)
    private TextView mButton1;

    private TextView mButton2;

    private TextView mButton3;

    private TextView mButton4;

    private TextView mButton5;

    private TextView mButton6;

    private TextView mButton7;

    private TextView mButton8;

    private TextView mButton9;

    private TextView mButton0;

    private TextView mButtonBack;

    private ImageView mButtonDelete;

    private ImageView mFingerPrint;


    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    SparseArray<String> keyValues = new SparseArray<>();

    // Our communication link to the EditText
    InputConnection inputConnection;

    private void init(Context context, AttributeSet attrs) {
        customRippleEffect = new CustomRippleEffect(context);
        setBackground(customRippleEffect);
        LayoutInflater.from(context).inflate(R.layout.pin_keyboard, this, true);
        mButton1 = (TextView) findViewById(R.id.textView1);
        mButton2 = (TextView) findViewById(R.id.textView2);
        mButton3 = (TextView) findViewById(R.id.textView3);
        mButton4 = (TextView) findViewById(R.id.textView4);
        mButton5 = (TextView) findViewById(R.id.textView5);
        mButton6 = (TextView) findViewById(R.id.textView6);
        mButton7 = (TextView) findViewById(R.id.textView7);
        mButton8 = (TextView) findViewById(R.id.textView8);
        mButton9 = (TextView) findViewById(R.id.textView9);
        mButton0 = (TextView) findViewById(R.id.textView0);
        mButtonBack = (TextView) findViewById(R.id.ivViewBack);
        mButtonDelete = (ImageView) findViewById(R.id.ivViewCancel);
        mFingerPrint = (ImageView) findViewById(R.id.ivFingerPrint);
    /*    mButton1.setText(Utils.getDecimalFormatted(mButton1.getText().toString(),"", Utils.getUserSetLocale()));
        mButton2.setText(Utils.getDecimalFormatted(mButton2.getText().toString(),"", Utils.getUserSetLocale()));
        mButton3.setText(Utils.getDecimalFormatted(mButton3.getText().toString(),"", Utils.getUserSetLocale()));
        mButton4.setText(Utils.getDecimalFormatted(mButton4.getText().toString(),"", Utils.getUserSetLocale()));
        mButton5.setText(Utils.getDecimalFormatted(mButton5.getText().toString(),"", Utils.getUserSetLocale()));
        mButton6.setText(Utils.getDecimalFormatted(mButton6.getText().toString(),"", Utils.getUserSetLocale()));
        mButton7.setText(Utils.getDecimalFormatted(mButton7.getText().toString(),"", Utils.getUserSetLocale()));
        mButton8.setText(Utils.getDecimalFormatted(mButton8.getText().toString(),"", Utils.getUserSetLocale()));
        mButton9.setText(Utils.getDecimalFormatted(mButton9.getText().toString(),"", Utils.getUserSetLocale()));
        mButton0.setText(Utils.getDecimalFormatted(mButton0.getText().toString(),"", Utils.getUserSetLocale()));*/
        // set button click listeners
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButton0.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);
        mFingerPrint.setOnClickListener(this);
        mButtonDelete.setOnLongClickListener(this);

        // map buttons IDs to input strings
        keyValues.put(R.id.textView1, "1");
        keyValues.put(R.id.textView2, "2");
        keyValues.put(R.id.textView3, "3");
        keyValues.put(R.id.textView4, "4");
        keyValues.put(R.id.textView5, "5");
        keyValues.put(R.id.textView6, "6");
        keyValues.put(R.id.textView7, "7");
        keyValues.put(R.id.textView8, "8");
        keyValues.put(R.id.textView9, "9");
        keyValues.put(R.id.textView0, "0");
//        keyValues.put(R.id.textView00, "00");
    }

    @Override
    public void onClick(View v) {
        listener.onRequestFocus();
        customRippleEffect.createRipple(v);
        // do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return;


        if (v.getId() == R.id.ivFingerPrint) {
            listener.onFingerPrintCLick();
            return;
        }
        // Delete text or input key value
        // All communication goes through the InputConnection
        if (v.getId() == R.id.ivViewCancel) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                // no selection, so delete previous character
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                // delete the selection
                inputConnection.commitText("", 1);
            }
        } else if (v.getId() == R.id.ivViewBack) {
            listener.onBackClickListener();
        } else {
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, value.length());

        }
    }

    public void setInputConnection(InputConnection ic) {
        System.out.println("set input connection");
        this.inputConnection = ic;
    }

    @Override
    public boolean onLongClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        view.setHapticFeedbackEnabled(true);
        listener.onLongClickListener();
        return true;
    }

    public void setmButton00Visibility() {
        mButtonBack.setVisibility(INVISIBLE);
    }

    public void setmFingerPrintVisibility(boolean isVisible) {
        if (isVisible) {
            mButtonDelete.setVisibility(GONE);
            mFingerPrint.setVisibility(VISIBLE);
        } else {
            mButtonDelete.setVisibility(VISIBLE);
            mFingerPrint.setVisibility(GONE);

        }
    }


    public interface Listener {
        void onLongClickListener();

        void onBackClickListener();

        void onRequestFocus();

        void onFingerPrintCLick();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
