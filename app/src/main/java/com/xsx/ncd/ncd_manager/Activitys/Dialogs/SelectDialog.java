package com.xsx.ncd.ncd_manager.Activitys.Dialogs;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xsx.ncd.ncd_manager.Activitys.Listeners.DialogCancelListener;
import com.xsx.ncd.ncd_manager.Activitys.Listeners.DialogItemSelectListener;
import com.xsx.ncd.ncd_manager.R;

import java.util.ArrayList;

public class SelectDialog extends DialogFragment {

    private TextView dialogTitleTextView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button dialogCancelButtoon;

    private Bundle args = null;
    private Context context = null;

    private DialogItemSelectListener<String> dialogItemSelectListener = null;
    private DialogCancelListener dialogCancelListener = null;


    public void addDialogItemSelectListener(DialogItemSelectListener<String> listener){
        dialogItemSelectListener = listener;
    }

    public void addDialogCancelListener(DialogCancelListener listener){
        dialogCancelListener = listener;
    }

    public static SelectDialog newInstance(Context context) {
        SelectDialog f = new SelectDialog();

        // Supply num input as an argument.
        f.args = new Bundle();
        f.args.putInt(DialogStringDefine.DIALOG_ARGS_USER_VALUE_KEY_STRING, 0);

        f.setArguments(f.args);
        f.context = context;

        f.adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1 , new ArrayList<>());

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_layout, container);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogTitleTextView = view.findViewById(R.id.select_dialog_TitleTextView);


        listView = view.findViewById(R.id.select_dialog_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if(dialogItemSelectListener != null)
                    dialogItemSelectListener.onItemSelectted(args.getStringArray(DialogStringDefine.DIALOG_ARGS_ITEM_ARRAY_KEY_STRING)[position],
                            args.getString(DialogStringDefine.DIALOG_ARGS_USER_VALUE_KEY_STRING));
            }
        });

        dialogCancelButtoon = view.findViewById(R.id.select_dialog_cancel_button);
        dialogCancelButtoon.setOnClickListener(v -> {
            dismiss();
            if(dialogCancelListener != null)
                dialogCancelListener.onCancel();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        dialogTitleTextView.setText(getArguments().getString(DialogStringDefine.DIALOG_ARGS_TITLE_KEY_STRING));

        adapter.clear();
        adapter.addAll(args.getStringArray(DialogStringDefine.DIALOG_ARGS_ITEM_ARRAY_KEY_STRING));
    }

    public void showDialog(FragmentManager manager, String tag, String title, String[] itemArray, String userValue) {

        args.putString(DialogStringDefine.DIALOG_ARGS_TITLE_KEY_STRING, title);
        args.putStringArray(DialogStringDefine.DIALOG_ARGS_ITEM_ARRAY_KEY_STRING, itemArray);
        args.putString(DialogStringDefine.DIALOG_ARGS_USER_VALUE_KEY_STRING, userValue);

        show(manager, tag);
    }
}
