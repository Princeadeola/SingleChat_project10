package com.adeolaadesipe.singlechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;

import com.adeolaadesipe.singlechat.adapter.MessagesAdapter;
import com.adeolaadesipe.singlechat.model.Messages;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private final List<Messages> messages = new ArrayList<>();
    private String name;
    private String number;
    private String email;
    private boolean dataSet = false;
    private String chatKey = "";
    String lastMessage = "";
    int unseenMessages = 0;

    private RecyclerView messageRV;
    CircleImageView userProfilePicture;
    DatabaseReference reference;
    MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageRV = findViewById(R.id.messagesRVID);
        userProfilePicture = findViewById(R.id.userProfilePicsID);
        reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://singlechatapp-a1057-default-rtdb.firebaseio.com");

        number = getIntent().getStringExtra("number");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");

        messageRV.setHasFixedSize(true);
        messageRV.setLayoutManager(new LinearLayoutManager(this));

        messagesAdapter = new MessagesAdapter(messages, MainActivity.this);
        messageRV.setAdapter(messagesAdapter);

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        dialog.show();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                final String profilePicsUrl = snapshot.child("users").child(number).child("profile_pic").getValue(String.class);
//                final String profilePicsUrl = snapshot.child("users").child(number).child("profile_pic").getValue(String.class);
//                Picasso.get().load(profilePicsUrl).placeholder(getApplicationContext().getResources().getDrawable(R.drawable.profile)).error(getApplicationContext().getResources().getDrawable(R.drawable.profile)).into(userProfilePicture);
//                Picasso.get().load(profilePicsUrl).into(userProfilePicture);

//                if (!profilePicsUrl.isEmpty()){
//                    //Picasso.get().load(profilePicsUrl).into(userProfilePicture);
//                    Picasso.get().load("https://imgs.search.brave.com/tWsF50Woy6Sdk1JtgUaRtxMAYmm9XscaJo19cduPv0Q/rs:fit:512:512:1/g:ce/aHR0cHM6Ly9jZG4y/Lmljb25maW5kZXIu/Y29tL2RhdGEvaWNv/bnMvYXZhdGFycy05/OS82Mi9hdmF0YXIt/MzY5LTQ1NjMyMS01/MTIucG5n").placeholder(getApplicationContext().getResources().getDrawable(R.drawable.profile)).error(getApplicationContext().getResources().getDrawable(R.drawable.profile)).into(userProfilePicture);
//                }else {
//                    Picasso.get().load("https://imgs.search.brave.com/tWsF50Woy6Sdk1JtgUaRtxMAYmm9XscaJo19cduPv0Q/rs:fit:512:512:1/g:ce/aHR0cHM6Ly9jZG4y/Lmljb25maW5kZXIu/Y29tL2RhdGEvaWNv/bnMvYXZhdGFycy05/OS82Mi9hdmF0YXIt/MzY5LTQ1NjMyMS01/MTIucG5n").placeholder(getApplicationContext().getResources().getDrawable(R.drawable.profile)).error(getApplicationContext().getResources().getDrawable(R.drawable.profile)).into(userProfilePicture);
//                }
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                unseenMessages = 0;
                lastMessage = "";
                chatKey = "";

                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()){
                    final String getNumber = dataSnapshot.getKey();

                    dataSet = false;
                    if (!getNumber.equals(number)){
                        final String getName = dataSnapshot.child("name").getValue(String.class);
                        final String getProfilePics = dataSnapshot.child("profile_pic").getValue(String.class);
                        reference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int getChatCounts = (int) snapshot.getChildrenCount();

                                if (getChatCounts > 0){
                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                        String getKey = dataSnapshot1.getKey();
                                        chatKey = getKey;

                                        if (dataSnapshot1.hasChild("user_1") && dataSnapshot1.hasChild("user_2") && dataSnapshot1.hasChild("messages")) {
                                            String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                            String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                                            if ((getUserOne.equals(getNumber) && getUserTwo.equals(number)) || (getUserOne.equals(number) && getUserTwo.equals(getNumber))) {

                                                for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()) {

                                                    final long getMessageKey = Long.parseLong(chatDataSnapshot.getKey());
                                                    final long getLastSeenMessage = Long.parseLong(MemoryData.getLastMessage(MainActivity.this, getKey));

                                                    lastMessage = chatDataSnapshot.child("msg").getValue(String.class);
                                                    if (getMessageKey > getLastSeenMessage) {
                                                        unseenMessages++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                if (!dataSet) {
                                    dataSet = true;
                                    Messages messagesList = new Messages(getName, getNumber, lastMessage, getProfilePics, unseenMessages, chatKey);
                                    messages.add(messagesList);
                                    messagesAdapter.updateData(messages);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}