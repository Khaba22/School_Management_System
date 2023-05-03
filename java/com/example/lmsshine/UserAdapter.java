package com.example.lmsshine;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserAdapter extends ArrayAdapter {

    private Activity mContext;
    List<User> userList;

    public UserAdapter(Activity mContext, List<User> userList) {
        super(mContext, R.layout.activity_admin_profile, userList);
        this.mContext = mContext;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listUserView = inflater.inflate(R.layout.activity_admin_profile, null, true);

        TextView tvName = listUserView.findViewById(R.id.fullNameTitle);
        TextView tvSchool = listUserView.findViewById(R.id.schoolNameNameTitle);
        TextView tvEmail = listUserView.findViewById(R.id.emailTitle);

        User user = userList.get(position);

        tvName.setText(user.getFullName());
        tvSchool.setText(user.getSchoolName());
        tvEmail.setText(user.getEmail());

        return  listUserView;

    }
}
