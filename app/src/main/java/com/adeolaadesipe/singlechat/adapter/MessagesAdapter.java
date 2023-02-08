package com.adeolaadesipe.singlechat.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adeolaadesipe.singlechat.R;
import com.adeolaadesipe.singlechat.ChatActivity;
import com.adeolaadesipe.singlechat.model.Messages;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private List<Messages> messages;
    private final Context context;

    public MessagesAdapter(List<Messages> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MyViewHolder holder, int position) {
        Messages list = messages.get(position);

//        if (!list.getProfilePic().isEmpty()){
//            Picasso.get().load(list.getProfilePic()).into(holder.userProfilePics);
//        }
            Picasso.get().load("https://imgs.search.brave.com/tWsF50Woy6Sdk1JtgUaRtxMAYmm9XscaJo19cduPv0Q/rs:fit:512:512:1/g:ce/aHR0cHM6Ly9jZG4y/Lmljb25maW5kZXIu/Y29tL2RhdGEvaWNv/bnMvYXZhdGFycy05/OS82Mi9hdmF0YXIt/MzY5LTQ1NjMyMS01/MTIucG5n").into(holder.userProfilePics);
//        Picasso.get().load("https://imgs.search.brave.com/tWsF50Woy6Sdk1JtgUaRtxMAYmm9XscaJo19cduPv0Q/rs:fit:512:512:1/g:ce/aHR0cHM6Ly9jZG4y/Lmljb25maW5kZXIu/Y29tL2RhdGEvaWNv/bnMvYXZhdGFycy05/OS82Mi9hdmF0YXIt/MzY5LTQ1NjMyMS01/MTIucG5n").placeholder(getApplicationContext().getResources().getDrawable(R.drawable.profile)).error(getApplicationContext().getResources().getDrawable(R.drawable.profile)).into(userProfilePicture);


        holder.userName.setText(list.getName());
        holder.userLastMessage.setText(list.getLastMessage());

        if (list.getUnseenMessages() == 0){
            holder.unseenMessage.setVisibility(View.GONE);
            holder.userLastMessage.setTextColor(Color.parseColor("#959595"));
        }else {
            holder.unseenMessage.setVisibility(View.VISIBLE);
            holder.unseenMessage.setText(list.getUnseenMessages()+"");
            holder.userLastMessage.setTextColor(context.getResources().getColor(R.color.theme_color_80));
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("number", list.getNumber());
                intent.putExtra("name", list.getName());
                intent.putExtra("chat_key", list.getChatKey());
                //intent.putExtra("profile_pic", list.getProfilePic());

                context.startActivity(intent);
            }
        });
    }

    public void updateData(List<Messages> messages){
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView userProfilePics;
        private TextView userName, userLastMessage, unseenMessage;
        private LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userProfilePics = itemView.findViewById(R.id.user_profile);
            userName = itemView.findViewById(R.id.userNameID);
            userLastMessage = itemView.findViewById(R.id.lastMessageID);
            unseenMessage = itemView.findViewById(R.id.unseenMessageID);
            layout = itemView.findViewById(R.id.mainLinearLayout);
        }
    }
}
