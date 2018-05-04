package com.xsx.ncd.ncd_manager.Activitys.Adapter;


import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.entity.User;

import java.util.List;

public class UserAdapter extends BaseItemDraggableAdapter<User, BaseViewHolder> {

    public UserAdapter(List<User> userList, int itemLayoutId) {
        super(itemLayoutId, userList);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {

        helper.setText(R.id.listViewUserNameTextView, item.getName());
    }

}
