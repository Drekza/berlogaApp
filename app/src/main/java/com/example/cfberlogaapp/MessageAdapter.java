package com.example.cfberlogaapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

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
    public int getItemViewType(int position) {
        if(messages.get(position).isMine()){
            return 1;
        }
        else{
            return 2;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(viewType == 1){
            view = inflater.inflate(R.layout.my_message_layout, parent, false);
            return new MineMessageViewHolder(view);
        }else {
            view = inflater.inflate(R.layout.their_message_layout, parent, false);
            return new TheirMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == 1){
            ((MineMessageViewHolder) holder).SetMyMessage(messages.get(position));
        }else {
            ((TheirMessageViewHolder) holder).SetTheirMessage(messages.get(position));
        }
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }



    class MineMessageViewHolder extends RecyclerView.ViewHolder{
        private TextView messageTextView;

        MineMessageViewHolder(@NonNull View itemView){
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }

        private void SetMyMessage(Message message){
            messageTextView.setText(message.getMessageText());
        }
    }



    class TheirMessageViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView userPictureImageView;
        private TextView userNameTextView;
        private TextView messageTextView;

        TheirMessageViewHolder(@NonNull View itemView){
            super(itemView);

            userPictureImageView = itemView.findViewById(R.id.userPic);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }

        private void SetTheirMessage(Message message){
            message.setImageViewWithProfilePicture(context, userPictureImageView);
            message.setTextViewWithUserName(userNameTextView);
            messageTextView.setText(message.getMessageText());
        }
    }
}