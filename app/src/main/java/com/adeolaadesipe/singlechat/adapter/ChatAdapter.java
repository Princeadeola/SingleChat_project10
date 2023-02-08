package com.adeolaadesipe.singlechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adeolaadesipe.singlechat.MemoryData;
import com.adeolaadesipe.singlechat.R;
import com.adeolaadesipe.singlechat.model.Chats;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    List<Chats> chats;
    Context context;
    String userNumber;

    public ChatAdapter(List<Chats> chats, Context context) {
        this.chats = chats;
        this.context = context;
        this.userNumber = MemoryData.getData(context);
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        Chats list = chats.get(position);

        if (list.getNumber().equals(userNumber)){
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.userLayout.setVisibility(View.GONE);

            holder.myMessage.setText(list.getMessage());
            holder.myMessageTime.setText(list.getDate() + " " + list.getTime());
        }else{
            holder.myLayout.setVisibility(View.GONE);
            holder.userLayout.setVisibility(View.VISIBLE);

            holder.userMessage.setText(list.getMessage());
            holder.userMessageTime.setText(list.getDate() + " " + list.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void updateChat(List<Chats> chats){
        this.chats = chats;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout userLayout, myLayout;
        TextView userMessage, myMessage, userMessageTime, myMessageTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userLayout = itemView.findViewById(R.id.userLayout);
            myLayout = itemView.findViewById(R.id.myLayout);
            userMessage = itemView.findViewById(R.id.userMessageID);
            myMessage = itemView.findViewById(R.id.myMessageID);
            userMessageTime = itemView.findViewById(R.id.userMessageTimeID);
            myMessageTime = itemView.findViewById(R.id.myMessageTimeID);
        }
    }
}
