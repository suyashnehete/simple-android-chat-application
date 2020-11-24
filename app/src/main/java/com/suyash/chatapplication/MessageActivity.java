package com.suyash.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suyash.chatapplication.Adapter.MessageAdapter;
import com.suyash.chatapplication.Model.ChatList;
import com.suyash.chatapplication.Model.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profileImg;
    TextView userName;
    EditText message;
    ImageButton sendBtn;
    RecyclerView list;

    String receiverId;
    String receiverName;
    String receiverProfile;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    List<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        profileImg = findViewById(R.id.profile_img);
        userName = findViewById(R.id.username);
        message = findViewById(R.id.message);
        sendBtn = findViewById(R.id.send_btn);
        list = findViewById(R.id.list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MessageActivity.this);
        list.setLayoutManager(linearLayoutManager);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        receiverId = getIntent().getStringExtra("id");
        receiverName = getIntent().getStringExtra("name");
        receiverProfile = getIntent().getStringExtra("profile");

        userName.setText(receiverName);
        if (!receiverProfile.equals("")){
            Glide.with(MessageActivity.this).load(receiverProfile).into(profileImg);
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        mDatabase.child("Message")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() != 0){
                            messageList.clear();
                            for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                Message message = snapshot1.getValue(Message.class);
                                if (
                                        (mAuth.getCurrentUser().getUid().equals(message.getSenderId()) && receiverId.equals(message.getReceiverId()))
                                        ||
                                                (mAuth.getCurrentUser().getUid().equals(message.getReceiverId()) && receiverId.equals(message.getSenderId()))
                                ){
                                    messageList.add(message);
                                    list.setAdapter(new MessageAdapter(MessageActivity.this, messageList));
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void sendMessage(){
        String msg = message.getText().toString().trim();
        if (TextUtils.isEmpty(msg)){
            Toast.makeText(this, "Empty Message Cannot Be Sent", Toast.LENGTH_SHORT).show();
        }else{
            String key = mDatabase.child("Message").push().getKey();
            mDatabase.child("Message").child(key).setValue(new Message(
                    msg,
                    mAuth.getCurrentUser().getUid(),
                    receiverId,
                    Calendar.getInstance().getTime().toString()
            ));
            message.setText("");

            mDatabase.child("chats").child(mAuth.getCurrentUser().getUid()).child(receiverId).setValue(new ChatList(
                    receiverId,
                    receiverName,
                    receiverProfile
            ));
        }
    }
}