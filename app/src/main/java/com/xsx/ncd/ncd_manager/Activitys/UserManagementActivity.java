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

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.xsx.ncd.ncd_manager.Activitys.Adapter.UserAdapter;
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

public class UserManagementActivity extends Activity implements AddUserDialog.AddUserDialogActionListener {

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
    @BindView(R.id.btnWoman)
    RadioButton btnWoman;
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

    private Observer<Boolean> addUserObserver;

    private UserAdapter userAdapter;
    private Observer<List<User>> userListObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        ButterKnife.bind(this);

        addUserDialog = new AddUserDialog();
        waitDialog = new WaitDialog();

        addUserImageView.setOnClickListener(v -> {
            addUserDialog.show(getFragmentManager(), "xsx");
        });


        addUserObserver = new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                Log.d("xsx", "add user success");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("xsx", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        userAdapter = new UserAdapter(null, R.layout.layout_user_listview_item);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(userAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(userRecyclerView);


        // 开启滑动删除
        userAdapter.enableSwipeItem();
        userAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d("xsx", "onItemSwipeStart= " + pos);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d("xsx", "clearView= " + pos);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d("xsx", "onItemSwiped= " + pos);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                Log.d("xsx", "onItemSwipeMoving= " + viewHolder.getAdapterPosition());
            }
        });


        userRecyclerView.setAdapter(userAdapter);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userListObserver = new Observer<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<User> users) {
                userAdapter.setNewData(users);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("xsx", "query all user error: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        freshUserListImageView.setOnClickListener(v -> {
            showAllUser();
        });
    }

    @Override
    public void onSubmitSaveUser(User user) {
        addNewUser(user);
    }

    @Override
    public void onCancelSaveUser() {
        Log.d("xsx", "cancel user");
    }

    private void addNewUser(User user) {
        waitDialog.show(getFragmentManager(), "wait");


        DataBaseMethods.getInstance().addNewUser(addUserObserver, user);

        waitDialog.dismiss();
    }

    private void showAllUser() {
        DataBaseMethods.getInstance().queryAllUser(userListObserver);
    }
}
