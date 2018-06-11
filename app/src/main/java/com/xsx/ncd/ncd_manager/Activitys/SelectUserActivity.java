package com.xsx.ncd.ncd_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.xsx.ncd.ncd_manager.Activitys.Adapter.UserAdapter;
import com.xsx.ncd.ncd_manager.Activitys.Adapter.UserDecoration;
import com.xsx.ncd.ncd_manager.Activitys.UserManagementActivity;
import com.xsx.ncd.ncd_manager.Dao.DataBaseMethods;
import com.xsx.ncd.ncd_manager.entity.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SelectUserActivity extends Activity {

    @BindView(R.id.freshUserListImageView)
    ImageView freshUserListImageView;
    @BindView(R.id.editUserImageView)
    ImageView editUserImageView;
    @BindView(R.id.userRecyclerView)
    RecyclerView userRecyclerView;

    private UserAdapter userAdapter;
    private Observer<List<User>> userListObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        ButterKnife.bind(this);

        userAdapter = new UserAdapter(null, this, R.layout.layout_user_listview_item);
        userAdapter.setOnItemClickListener((v, position, user)->{
            Log.d("xsx", "click: "+user.getName());
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
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SelectUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

        editUserImageView.setOnClickListener(v ->{
            startActivity(new Intent(SelectUserActivity.this, UserManagementActivity.class));
        });

        DataBaseMethods.getInstance().queryAllUser(userListObserver);
    }
}
