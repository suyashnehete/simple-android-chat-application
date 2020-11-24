package com.suyash.chatapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.suyash.chatapplication.MessageActivity;
import com.suyash.chatapplication.Model.ChatList;
import com.suyash.chatapplication.Model.User;
import com.suyash.chatapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder>{

    List<ChatList> users;
    Context context;

    public ChatsAdapter(List<ChatList> users, Context context) {
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
        final ChatList user = users.get(position);

        holder.userName.setText(user.getName());

        if (!user.getProfile().equals("")){
            Glide.with(context).load(user.getProfile()).into(holder.profileImage);
        }

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("id", user.getId());
                i.putExtra("name", user.getName());
                i.putExtra("profile", user.getProfile() == null ? "" : user.getProfile());
                context.startActivity(i);
            }
        });

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
