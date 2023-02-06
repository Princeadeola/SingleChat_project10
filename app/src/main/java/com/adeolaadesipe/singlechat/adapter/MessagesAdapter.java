package com.adeolaadesipe.singlechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adeolaadesipe.singlechat.R;
import com.adeolaadesipe.singlechat.model.Messages;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private final List<Messages> messages;
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

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //private CircleImageView userProfilePics;
        private TextView userName, userLastMessage, unseenMessage;
        private LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //userProfilePics = itemView.findViewById(R.id.user_profile);
            userName = itemView.findViewById(R.id.userNameID);
            userLastMessage = itemView.findViewById(R.id.lastMessageID);
            unseenMessage = itemView.findViewById(R.id.unseenMessageID);
        }
    }
}
