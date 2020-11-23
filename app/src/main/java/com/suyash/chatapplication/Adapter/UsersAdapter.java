package com.suyash.chatapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.suyash.chatapplication.Model.User;
import com.suyash.chatapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    List<User> users;
    Context context;

    public UsersAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);

        holder.userName.setText(user.getName());

        if (user.getProfile() != null){
            Glide.with(context).load(user.getProfile()).into(holder.profileImage);
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profileImage;
        TextView userName;
        View root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView;
            profileImage = itemView.findViewById(R.id.profile_img);
            userName = itemView.findViewById(R.id.username);

        }
    }
}
