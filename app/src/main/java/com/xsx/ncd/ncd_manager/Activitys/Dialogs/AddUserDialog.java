package com.xsx.ncd.ncd_manager.Activitys.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.entity.User;


public class AddUserDialog extends DialogFragment {

    EditText userNameEditView;
    EditText userIdEditView;
    EditText userAgeEditView;
    RadioGroup userSexRadioGroup;
    RadioButton userSexMenRadioButton;
    EditText userPhoneEditText;
    EditText userDepEditText;
    Button cancelAddUserButton;
    Button submitAddUserButton;

    private AddUserDialogActionListener addUserDialogActionListener;
    private User user;

    public interface AddUserDialogActionListener {
        public void onSubmitSaveUser(User user);
        public void onCancelSaveUser();
    }

    @Override
    public void onAttach(Context context) {
        try{
            addUserDialogActionListener = (AddUserDialogActionListener)context;
        }catch(ClassCastException e){
            throw  new ClassCastException(context.toString()
                    + " must implement AddUserDialogActionListener");
        }
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        try{
            addUserDialogActionListener = (AddUserDialogActionListener)activity;
        }catch(ClassCastException e){
            throw  new ClassCastException(activity.toString()
                    + " must implement AddUserDialogActionListener");
        }

        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_user, container);

        userNameEditView = view.findViewById(R.id.userNameEditView);
        userIdEditView = view.findViewById(R.id.userIdEditView);
        userAgeEditView = view.findViewById(R.id.userAgeEditView);
        userSexMenRadioButton = view.findViewById(R.id.userSexMenRadioButton);
        userSexRadioGroup = view.findViewById(R.id.userSexRadioGroup);
        userPhoneEditText = view.findViewById(R.id.userPhoneEditText);
        userDepEditText = view.findViewById(R.id.userDepEditText);
        //cancelAddUserButton = view.findViewById(R.id.cancelAddUserButton);
        //submitAddUserButton = view.findViewById(R.id.submitAddUserButton);

        userNameEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    submitAddUserButton.setEnabled(true);
                else
                    submitAddUserButton.setEnabled(false);
            }
        });

        user = new User();

        submitAddUserButton.setOnClickListener(v->{
            submitAction();

            dismiss();
        });

        cancelAddUserButton.setOnClickListener(v->{
            addUserDialogActionListener.onCancelSaveUser();
            dismiss();
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        if(addUserDialogActionListener != null)
            addUserDialogActionListener.onCancelSaveUser();

        user = null;

        super.onDestroyView();
    }

    private void submitAction(){
        user.setName(userNameEditView.getText().toString());
        user.setUserid(userIdEditView.getText().toString());
        user.setAge(userAgeEditView.getText().toString());
        user.setMen(userSexMenRadioButton.isChecked());
        user.setDep(userDepEditText.getText().toString());
        user.setPhone(userPhoneEditText.getText().toString());

        addUserDialogActionListener.onSubmitSaveUser(user);
    }
}
