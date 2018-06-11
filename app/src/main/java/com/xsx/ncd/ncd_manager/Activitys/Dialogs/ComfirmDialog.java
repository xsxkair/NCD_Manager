package com.xsx.ncd.ncd_manager.Activitys.Dialogs;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xsx.ncd.ncd_manager.R;

public class ComfirmDialog extends DialogFragment {

    private TextView dialogTitleTextView;
    private TextView dialogContentTextView;
    private Button dialogConfirmButtoon;

    private ComfirmDialogSubmitListener comfirmDialogSubmitListener = null;

    public interface ComfirmDialogSubmitListener {
        void onSubmit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_layout, container, false);

        dialogTitleTextView = view.findViewById(R.id.confirmDialogTitleTextView);
        dialogContentTextView = view.findViewById(R.id.confirmDialogContentTextView);
        dialogConfirmButtoon = view.findViewById(R.id.confirmDialogOKButton);

        Log.d("xsx", dialogTitleTextView.getText().toString());

        dialogConfirmButtoon.setOnClickListener(v -> {
            super.dismiss();
            if(comfirmDialogSubmitListener != null)
                comfirmDialogSubmitListener.onSubmit();
        });

        return view;
    }

    public void showComfirmDialog(FragmentManager manager, String tag, String title, String content) {

        dialogTitleTextView.setText(title);
        dialogContentTextView.setText(content);

        show(manager, tag);
    }

}
