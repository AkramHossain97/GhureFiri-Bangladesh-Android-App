package com.example.ghurefiribangladesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SinglePostActivity extends AppCompatActivity {

    private ImageView mPostImage;
    private TextView mPostTitle;
    private TextView mPostDescription;
    private Button mBtnRemove;

    private DatabaseReference mDatabase ;
    private String mPost_key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Post");
        mPost_key = getIntent().getExtras().getString("Post_id");

        mPostImage = (ImageView) findViewById(R.id.PostImage);
        mPostTitle = (TextView) findViewById(R.id.Post_title);
        mPostDescription = (TextView) findViewById(R.id.Post_Description);
        mBtnRemove=(Button) findViewById(R.id.RemovePost);



        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String package_title = (String) snapshot.child("postname").getValue();
                String package_description = (String) snapshot.child("posttext").getValue();
                String package_image = (String) snapshot.child("post_image").getValue();

                mPostTitle.setText(package_title);
                mPostDescription.setText(package_description);
                Picasso.get().load(package_image).into(mPostImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SinglePostActivity.this);
                builder.setMessage("Are you sure you want to remove the post ? ").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.child(mPost_key).removeValue();
                        Intent ToCurrentUserPost = new Intent(SinglePostActivity.this,CurrentUserPosts.class);
                        finish();
                        startActivity(ToCurrentUserPost);
                        

                    }
                }).setNegativeButton("Cancel",null);
                AlertDialog alert=builder.create();
                alert.show();

            }
        });

    }
}