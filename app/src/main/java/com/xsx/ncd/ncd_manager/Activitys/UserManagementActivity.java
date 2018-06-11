package com.xsx.ncd.ncd_manager.Activitys;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.xsx.ncd.ncd_manager.Activitys.Adapter.UserAdapter;
import com.xsx.ncd.ncd_manager.Activitys.Adapter.UserDecoration;
import com.xsx.ncd.ncd_manager.Activitys.Dialogs.AddUserDialog;
import com.xsx.ncd.ncd_manager.Activitys.Dialogs.WaitDialog;
import com.xsx.ncd.ncd_manager.Dao.DataBaseMethods;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.entity.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UserManagementActivity extends Activity implements AddUserDialog.AddUserDialogActionListener{

    @BindView(R.id.addUserImageView)
    ImageView addUserImageView;
    @BindView(R.id.userRecyclerView)
    RecyclerView userRecyclerView;
    @BindView(R.id.freshUserListImageView)
    ImageView freshUserListImageView;
    @BindView(R.id.userNameEditView)
    EditText userNameEditView;
    @BindView(R.id.userIdEditView)
    EditText userIdEditView;
    @BindView(R.id.userAgeEditView)
    EditText userAgeEditView;
    @BindView(R.id.userSexMenRadioButton)
    RadioButton userSexMenRadioButton;
    @BindView(R.id.userSexWomenRadioButton)
    RadioButton userSexWomenRadioButton;
    @BindView(R.id.userSexRadioGroup)
    RadioGroup userSexRadioGroup;
    @BindView(R.id.userPhoneEditText)
    EditText userPhoneEditText;
    @BindView(R.id.userDepEditText)
    EditText userDepEditText;
    @BindView(R.id.deleteUserButton)
    Button deleteUserButton;
    @BindView(R.id.submitUserButton)
    Button submitUserButton;


    private AddUserDialog addUserDialog;
    private WaitDialog waitDialog;

    private Observer<Boolean> userActionObserver;

    private UserAdapter userAdapter;
    private Observer<List<User>> userListObserver;

    private User selectUser = null;
    private User newUser = new User();
    private User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        ButterKnife.bind(this);

        resetUserInfoView();

        addUserDialog = new AddUserDialog();

        addUserImageView.setOnClickListener(v -> {
            addUserDialog.show(getFragmentManager(), "xsx2");
            createNewUser();
        });

        userActionObserver = new Observer<Boolean>() {
            private Disposable disposable = null;
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Boolean aBoolean) {
                Toast.makeText(UserManagementActivity.this, R.string.actionSuccessText, Toast.LENGTH_SHORT).show();
                DataBaseMethods.getInstance().queryAllUser(userListObserver);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(UserManagementActivity.this, R.string.ActionFailText, Toast.LENGTH_SHORT).show();
                disposable.dispose();
            }

            @Override
            public void onComplete() {
                disposable.dispose();
            }
        };

        userAdapter = new UserAdapter(null, this, R.layout.layout_user_listview_item);
        userAdapter.setOnItemClickListener((v, position, user)->{
            Log.d("xsx", "click: "+position);
            selectUser(user);
        });
        userRecyclerView.setAdapter(userAdapter);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.addItemDecoration(new UserDecoration(2));
        userListObserver = new Observer<List<User>>() {
            private Disposable disposable = null;
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(List<User> users) {
                userAdapter.updateUserData(users);
                resetUserInfoView();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(UserManagementActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                disposable.dispose();
            }

            @Override
            public void onComplete() {
                disposable.dispose();
            }
        };

        freshUserListImageView.setOnClickListener(v -> {
            DataBaseMethods.getInstance().queryAllUser(userListObserver);
        });

        deleteUserButton.setOnClickListener(v -> {
            //add new user, delete nothing
            if(selectUser == null){
                ;
            }
            //delete user
            else{
                DataBaseMethods.getInstance().deleteUser(userActionObserver, currentUser);
            }
        });

        submitUserButton.setOnClickListener(v -> {

            fillCurrentUserInfo();

            if(currentUser == null)
                return;
            else if(currentUser.getName() == null || currentUser.getName().length() <= 0)
            {
                Toast.makeText(this, R.string.userNameIsRequired, Toast.LENGTH_SHORT).show();
            }

            //add new user
            if(selectUser == null){
                DataBaseMethods.getInstance().addNewUser(userActionObserver, currentUser);
            }
            //edit user info
            else{
                DataBaseMethods.getInstance().updateUser(userActionObserver, currentUser);
            }
        });

        DataBaseMethods.getInstance().queryAllUser(userListObserver);
    }

    private void selectUser(User user){
        selectUser = user;
        currentUser = selectUser;

        userNameEditView.setEnabled(false);
        userIdEditView.setEnabled(false);
        userAgeEditView.setEnabled(true);
        userSexMenRadioButton.setEnabled(true);
        userSexWomenRadioButton.setEnabled(true);
        userSexRadioGroup.setEnabled(true);
        userPhoneEditText.setEnabled(true);
        userDepEditText.setEnabled(true);
        deleteUserButton.setEnabled(true);
        submitUserButton.setEnabled(true);

        userNameEditView.setText(user.getName());
        userIdEditView.setText(user.getUserid());
        userAgeEditView.setText(user.getAge());
        userSexMenRadioButton.setChecked(user.getMen());
        userSexWomenRadioButton.setChecked(!user.getMen());
        userPhoneEditText.setText(user.getPhone());
        userDepEditText.setText(user.getDep());
    }

    private void createNewUser(){

        selectUser = null;
        currentUser = newUser;
        currentUser.resetUser();

        userNameEditView.setEnabled(true);
        userIdEditView.setEnabled(true);
        userAgeEditView.setEnabled(true);
        userSexMenRadioButton.setEnabled(true);
        userSexWomenRadioButton.setEnabled(true);
        userSexRadioGroup.setEnabled(true);
        userPhoneEditText.setEnabled(true);
        userDepEditText.setEnabled(true);
        deleteUserButton.setEnabled(false);
        submitUserButton.setEnabled(true);

        userNameEditView.setText(null);
        userIdEditView.setText(null);
        userAgeEditView.setText(null);
        userSexMenRadioButton.setChecked(true);
        userSexWomenRadioButton.setChecked(false);
        userPhoneEditText.setText(null);
        userDepEditText.setText(null);
    }

    private void fillCurrentUserInfo(){
        if(currentUser != null){
            currentUser.setName(userNameEditView.getText().toString());
            currentUser.setAge(userAgeEditView.getText().toString());
            currentUser.setMen(userSexMenRadioButton.isChecked());
            currentUser.setPhone(userPhoneEditText.getText().toString());
            currentUser.setDep(userDepEditText.getText().toString());
            currentUser.setUserid(userIdEditView.getText().toString());
        }
    }

    private void resetUserInfoView(){
        userNameEditView.setEnabled(false);
        userIdEditView.setEnabled(false);
        userAgeEditView.setEnabled(false);
        userSexMenRadioButton.setEnabled(false);
        userSexWomenRadioButton.setEnabled(false);
        userSexRadioGroup.setEnabled(false);
        userPhoneEditText.setEnabled(false);
        userDepEditText.setEnabled(false);
        deleteUserButton.setEnabled(false);
        submitUserButton.setEnabled(false);

        userNameEditView.setText(null);
        userIdEditView.setText(null);
        userAgeEditView.setText(null);
        userSexMenRadioButton.setChecked(true);
        userSexWomenRadioButton.setChecked(false);
        userPhoneEditText.setText(null);
        userDepEditText.setText(null);
    }

    @Override
    public void onSubmitSaveUser(User user) {

    }

    @Override
    public void onCancelSaveUser() {

    }
}
