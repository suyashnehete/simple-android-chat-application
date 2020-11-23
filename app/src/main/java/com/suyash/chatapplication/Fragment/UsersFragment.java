package com.suyash.chatapplication.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.suyash.chatapplication.Adapter.UsersAdapter;
import com.suyash.chatapplication.Model.User;
import com.suyash.chatapplication.R;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    RecyclerView userList;
    EditText searchUser;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    List<User> usersList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        userList = view.findViewById(R.id.user_list);
        searchUser = view.findViewById(R.id.search_user);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        userList.setLayoutManager(linearLayoutManager);

        listUsers();

        searchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    private void searchUsers(String s){
        if (s.equals("")){
            listUsers();
        }else{
            Query query = mDatabase.child("Users").orderByChild("searchName").startAt(s).endAt(s+"\uf8ff");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getChildrenCount() != 0){
                        usersList.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            if (snapshot1.getChildrenCount() != 0){
                                User user = snapshot1.getValue(User.class);
                                if (!user.getId().equals(mAuth.getCurrentUser().getUid())){
                                    usersList.add(user);
                                    userList.setAdapter(new UsersAdapter(usersList, getContext()));
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void listUsers(){
        mDatabase.child("Users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() != 0){
                            usersList.clear();
                            for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                if (snapshot1.getChildrenCount() != 0){
                                    User user = snapshot1.getValue(User.class);
                                    if (!user.getId().equals(mAuth.getCurrentUser().getUid())) {
                                        usersList.add(user);
                                        userList.setAdapter(new UsersAdapter(usersList, getContext()));
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}