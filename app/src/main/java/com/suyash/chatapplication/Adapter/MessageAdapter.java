package com.suyash.chatapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.suyash.chatapplication.Model.Message;
import com.suyash.chatapplication.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    Context context;
    List<Message> messageList;

    final static int LEFT_MSG = 1;
    final static int RIGHT_MSG = 0;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;
        if (viewType == RIGHT_MSG){
            view = layoutInflater.inflate(R.layout.message_right, parent, false);
        }else{
            view = layoutInflater.inflate(R.layout.message_left, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.message.setText(message.getMessage());
        holder.time.setText(message.getTime());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mAuth.getCurrentUser().getUid().equals(messageList.get(position).getSenderId())){
            return RIGHT_MSG;
        }else{
            return LEFT_MSG;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView message, time;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);

        }
    }
}
