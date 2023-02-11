package com.adeolaadesipe.singlechat;

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

import com.adeolaadesipe.singlechat.adapter.ChatAdapter;
import com.adeolaadesipe.singlechat.model.Chats;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    List<Chats> chatsLists;
    CircleImageView chatProfilePic, chatBackBtn;
    TextView userChatNameID, userChatOnlineStatus;
    ImageView sendChatMessageBtn;
    RecyclerView chatSectionRV;
    EditText chatMessageField;
    int generateChatKey;
    String getUserNumber;
    String chatKey;
    DatabaseReference reference;
    ChatAdapter chatAdapter;
    boolean loadingFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://singlechatapp-a1057-default-rtdb.firebaseio.com");
        chatsLists = new ArrayList<>();

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

        chatAdapter = new ChatAdapter(chatsLists, ChatActivity.this);
        chatSectionRV.setAdapter(chatAdapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (chatKey.isEmpty()) {
                    //generate chat key, be default it is 1
                    chatKey = "1";

                    if (snapshot.hasChild("chat")) {
                        if (snapshot.child("chat").child(chatKey).hasChild("messages")){
                            chatsLists.clear();

                            for (DataSnapshot messageSnapshot : snapshot.child("chat").child("messages").getChildren()){
                                if (messageSnapshot.hasChild("msg") &&  messageSnapshot.hasChild("number")){

                                    String messageTimeStamps = messageSnapshot.getKey();
                                    String getNumber = messageSnapshot.child("number").getValue(String.class);
                                    String getMessage = messageSnapshot.child("msg").getValue(String.class);


                                    Timestamp timestamp = new Timestamp(Long.parseLong(messageTimeStamps));
                                    Date date = new Date(timestamp.getTime());
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());

                                    Chats chatList = new Chats(getNumber, getName, getMessage, simpleTimeFormat.format(date), simpleDateFormat.format(date));
                                    chatsLists.add(chatList);

                                    if (loadingFirstTime || Long.parseLong(messageTimeStamps) > Long.parseLong(MemoryData.getLastMessage(ChatActivity.this, chatKey))){
                                        loadingFirstTime = false;

                                        // memory data to store the time stamp of the chat
                                        MemoryData.saveLastMessage(messageTimeStamps, chatKey, ChatActivity.this);
                                        chatAdapter.updateChat(chatsLists);

                                        chatSectionRV.scrollToPosition(chatsLists.size() - 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendChatMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getChatMessage = chatMessageField.getText().toString();

                //current timestamp
                String currentTimeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
                //MemoryData.saveLastMessage(currentTimeStamp, chatKey, ChatActivity.this);

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