package com.adeolaadesipe.singlechat.chats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adeolaadesipe.singlechat.MainActivity;
import com.adeolaadesipe.singlechat.MemoryData;
import com.adeolaadesipe.singlechat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    CircleImageView chatProfilePic, chatBackBtn;
    TextView userChatNameID, userChatOnlineStatus;
    ImageView sendChatMessageBtn;
    RecyclerView chatSectionRV;
    EditText chatMessageField;
    int generateChatKey;
    String getUserNumber;
    String chatKey;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://singlechatapp-a1057-default-rtdb.firebaseio.com");


        chatProfilePic = findViewById(R.id.chatUserProfilePic);
        chatBackBtn = findViewById(R.id.backBtnID);
        userChatNameID = findViewById(R.id.userChatNameID);
        userChatOnlineStatus = findViewById(R.id.usetOnlineStatusID);
        chatMessageField = findViewById(R.id.messageFieldID);
        sendChatMessageBtn = findViewById(R.id.sendMessageBtnID);
        chatSectionRV = findViewById(R.id.chatsRVID);

        String getName = getIntent().getStringExtra("name");
        String getNumber = getIntent().getStringExtra("number");
        chatKey = getIntent().getStringExtra("chat_key");
        //String getProfilePic = getIntent().getStringExtra("profile_pic");

        getUserNumber = MemoryData.getData(ChatActivity.this); //get user data from memory

        userChatNameID.setText(getName);
        //Picasso.get().load(getProfilePic).into(chatProfilePic);

        chatSectionRV.setHasFixedSize(true);
        chatSectionRV.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        if (chatKey.isEmpty()){
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //generate chat key, be default it is 1
                    chatKey = "1";

                    if (snapshot.hasChild("chat")){
                        chatKey = String.valueOf((snapshot.child("chat").getChildrenCount() + 1));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        sendChatMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getChatMessage = chatMessageField.getText().toString();

                String currentTimeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10); //current timestamp

                MemoryData.saveLastMessage(currentTimeStamp, chatKey, ChatActivity.this);

                reference.child("chat").child(chatKey).child("user_1").setValue(getUserNumber);
                reference.child("chat").child(chatKey).child("user_2").setValue(getNumber);
                reference.child("chat").child(chatKey).child("messages").child(currentTimeStamp).child("msg").setValue(getChatMessage);
                reference.child("chat").child(chatKey).child("messages").child(currentTimeStamp).child("number").setValue(getUserNumber);

                chatMessageField.setText("");
            }
        });


        chatBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}