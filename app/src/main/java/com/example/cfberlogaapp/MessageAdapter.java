package com.example.cfberlogaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends BaseAdapter {

    Context context;
    List<Message> messages = new ArrayList<>();

    MessageAdapter(Context context){
        this.context = context;
    }

    public void addMessage(Message message){
        this.messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Message message = messages.get(position);
        if(message.isMine()){
            convertView = inflater.inflate(R.layout.my_message_layout, null);
            TextView messageTextView = convertView.findViewById(R.id.messageTextView);
            messageTextView.setText(message.getMessageText());
        }else{
            convertView = inflater.inflate(R.layout.their_message_layout, null);
            TextView messageTextView = convertView.findViewById(R.id.messageTextView);
            TextView userNameTextView = convertView.findViewById(R.id.nameEditText);
            RoundedImageView userImageView = convertView.findViewById(R.id.userPic);
            messageTextView.setText(message.getMessageText());
            userNameTextView.setText(message.getUsersName());
        }
        return convertView;

    }
}