package com.example.github_search.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.URL;
import java.util.List;

import com.example.github_search.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserListViewModel extends ArrayAdapter<GitUser> {
    private int resource;
    public UserListViewModel(@NonNull Context context, int ressource, List<GitUser> data){
        super(context, ressource, data);
        this.resource = ressource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if(listViewItem == null)
            listViewItem = LayoutInflater.from(getContext()).inflate(resource, parent, false);

        CircleImageView imageViewUser = listViewItem.findViewById(R.id.imageViewAvatar);
        TextView textViewUsername = listViewItem.findViewById(R.id.textViewUsername);
        TextView textViewScore = listViewItem.findViewById(R.id.textViewScore);


        textViewUsername.setText(getItem(position).username);
        textViewScore.setText(String.valueOf(getItem(position).score));

        try {
            URL url = new URL(getItem(position).avatarUrl);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
            imageViewUser.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        return listViewItem;
    }
}

