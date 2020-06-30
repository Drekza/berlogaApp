package com.example.cfberlogaapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
            TextView userNameTextView = convertView.findViewById(R.id.userNameTextView);
            final RoundedImageView userImageView = convertView.findViewById(R.id.userPic);
            message.setImageViewWithProfilePicture(context, userImageView);
            messageTextView.setText(message.getMessageText());
            message.setTextViewWithUserName(userNameTextView);
        }
        return convertView;

    }
}