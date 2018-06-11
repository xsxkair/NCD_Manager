package com.xsx.ncd.ncd_manager.Activitys.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xsx.ncd.ncd_manager.R;
import com.xsx.ncd.ncd_manager.entity.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<User> userList;
    private int userSize;
    private LayoutInflater inflater;
    private int layoutResId;
    private UserHolder selectUserHolder = null;
    private int selectPosition = -1;
    private OnItemClickListener myOnItemClickListener;

    public UserAdapter(List<User> userList, Context context, int layoutResId) {
        this.updateUserData(userList);
        inflater = LayoutInflater.from(context);
        this.layoutResId = layoutResId;
    }

    public void updateUserData(List<User> userList){

        this.userList = userList;

        if(this.userList != null)
            this.userSize = this.userList.size();
        else
            this.userSize = 0;

        setSelectPosition(-1);
        selectUserHolder = null;

        this.notifyDataSetChanged();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(this.layoutResId, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        holder.userNameTextView.setText(userList.get(position).getName());

        if(myOnItemClickListener != null){
            holder.itemView.setOnClickListener(v -> {
                myOnItemClickListener.onItemClick(v, position, userList.get(position));

                if(selectUserHolder != null)
                    selectUserHolder.userHolderUnSelectedEvent();

                holder.userHolderSelectedEvent();
                selectUserHolder = holder;
                setSelectPosition(position);
            });
        }

        if(position == getSelectPosition())
            holder.userHolderSelectedEvent();
        else
            holder.userHolderUnSelectedEvent();
    }

    @Override
    public int getItemCount() {
        return this.userSize;
    }

    public UserHolder getSelectUserHolder() {
        return selectUserHolder;
    }

    public void setSelectUserHolder(UserHolder selectUserHolder) {
        this.selectUserHolder = selectUserHolder;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.myOnItemClickListener = onItemClickListener;
    }

    class UserHolder extends RecyclerView.ViewHolder{

        private View itemView;
        private TextView userNameTextView;

        public UserHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            userNameTextView = itemView.findViewById(R.id.listViewUserNameTextView);
        }

        public void userHolderSelectedEvent(){
            itemView.setBackgroundResource(R.color.button_pressed);
        }

        public void userHolderUnSelectedEvent(){
            itemView.setBackgroundResource(R.color.white);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position, User user);
    }
}
