package com.xsx.ncd.ncd_manager.Activitys.Dialogs;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.xsx.ncd.ncd_manager.Activitys.Listeners.DialogItemSelectListener;
import com.xsx.ncd.ncd_manager.R;

public class ComfirmDialog extends DialogFragment {

    private TextView dialogTitleTextView;
    private TextView dialogContentTextView;
    private Button dialogButtoon_1;
    private Button dialogButtoon_2;
    private Button dialogButtoon_3;

    private Bundle args = null;
    private DialogItemSelectListener<String> dialogItemSelectListener = null;

    public void setDialogItemSelectListener(DialogItemSelectListener<String> dialogItemSelectListener) {
        this.dialogItemSelectListener = dialogItemSelectListener;
    }

    public static ComfirmDialog newInstance() {
        ComfirmDialog f = new ComfirmDialog();

        // Supply num input as an argument.
        f.args = new Bundle();

        f.setArguments(f.args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_layout, container);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogTitleTextView = view.findViewById(R.id.confirmDialogTitleTextView);

        dialogContentTextView = view.findViewById(R.id.confirmDialogContentTextView);

        dialogButtoon_1 = view.findViewById(R.id.comfirm_dialog_button_1);
        dialogButtoon_1.setOnClickListener(v -> {
            super.dismiss();
            if(dialogItemSelectListener != null)
                dialogItemSelectListener.onItemSelectted(dialogButtoon_1.getText().toString(), args.getString(DialogStringDefine.DIALOG_ARGS_USER_VALUE_KEY_STRING));
        });

        dialogButtoon_2 = view.findViewById(R.id.comfirm_dialog_button_2);
        dialogButtoon_2.setOnClickListener(v -> {
            super.dismiss();
            if(dialogItemSelectListener != null)
                dialogItemSelectListener.onItemSelectted(dialogButtoon_2.getText().toString(), args.getString(DialogStringDefine.DIALOG_ARGS_USER_VALUE_KEY_STRING));
        });

        dialogButtoon_3 = view.findViewById(R.id.comfirm_dialog_button_3);
        dialogButtoon_3.setOnClickListener(v -> {
            super.dismiss();
            if(dialogItemSelectListener != null)
                dialogItemSelectListener.onItemSelectted(dialogButtoon_3.getText().toString(), args.getString(DialogStringDefine.DIALOG_ARGS_USER_VALUE_KEY_STRING));
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        dialogTitleTextView.setText(getArguments().getString(DialogStringDefine.DIALOG_ARGS_TITLE_KEY_STRING));
        dialogContentTextView.setText(args.getString(DialogStringDefine.DIALOG_ARGS_CONFIRM_CONTENT_KEY_STRING));

        String tempstring =  args.getString(DialogStringDefine.DIALOG_ARGS_BUTTON1_KEY_STRING);
        if(tempstring != null)
        {
            dialogButtoon_1.setText(tempstring);
            dialogButtoon_1.setVisibility(View.VISIBLE);
        }
        else
            dialogButtoon_1.setVisibility(View.GONE);

        tempstring =  args.getString(DialogStringDefine.DIALOG_ARGS_BUTTON2_KEY_STRING);
        if(tempstring != null)
        {
            dialogButtoon_2.setText(tempstring);
            dialogButtoon_2.setVisibility(View.VISIBLE);
        }
        else
            dialogButtoon_2.setVisibility(View.GONE);

        tempstring =  args.getString(DialogStringDefine.DIALOG_ARGS_BUTTON3_KEY_STRING);
        if(tempstring != null)
        {
            dialogButtoon_3.setText(tempstring);
            dialogButtoon_3.setVisibility(View.VISIBLE);
        }
        else
            dialogButtoon_3.setVisibility(View.GONE);

    }

    public void showComfirmDialog(FragmentManager manager, String tag, String title, String content, String button_1_text, String button_2_text, String button_3_text
            , String userValue) {

        args.putString(DialogStringDefine.DIALOG_ARGS_TITLE_KEY_STRING, title);
        args.putString(DialogStringDefine.DIALOG_ARGS_CONFIRM_CONTENT_KEY_STRING, content);
        args.putString(DialogStringDefine.DIALOG_ARGS_BUTTON1_KEY_STRING, button_1_text);
        args.putString(DialogStringDefine.DIALOG_ARGS_BUTTON2_KEY_STRING, button_2_text);
        args.putString(DialogStringDefine.DIALOG_ARGS_BUTTON3_KEY_STRING, button_3_text);
        args.putString(DialogStringDefine.DIALOG_ARGS_USER_VALUE_KEY_STRING, userValue);

        show(manager, tag);
    }
}
