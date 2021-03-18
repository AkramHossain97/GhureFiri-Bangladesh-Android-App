package com.example.ghurefiribangladesh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URL;

public class Profile extends AppCompatActivity {

    TextView nameLabel,emailLabel;
    FirebaseAuth mFirebaseAuth;
    ImageButton mBtnMyPosts;
    ImageButton mBtnBooking;
    ImageView profileImageView;
    ImageButton mBtnPackageBooking;
     FirebaseUser mCurrentuser;
    private DatabaseReference mDatabaseUser;
    GoogleSignInClient mGoogleSignInClient;
    private Uri mImageUri = null;
    private static final int GALLERY_REQUEST=1;
    private String email;
    private FirebaseDatabase Udatabase;
    private DatabaseReference mDatabase;
    private static final String USERS = "users";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String TAG = this.getClass().getName().toUpperCase();
    int TAKE_IMAGE_CODE=10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profile_image);
        nameLabel=findViewById(R.id.fullname_field);
        emailLabel=findViewById(R.id.email_field);
        mBtnMyPosts=findViewById(R.id.myposts);
        mBtnBooking=findViewById(R.id.BookedOrders);
        mBtnPackageBooking=findViewById(R.id.MypackageBookings);
        mFirebaseAuth=FirebaseAuth.getInstance();
        Udatabase= FirebaseDatabase.getInstance();
        mDatabaseUser=FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());


        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserProfileInfo userProfileInfo = snapshot.getValue(UserProfileInfo.class);
                nameLabel.setText(userProfileInfo.getUsername());
                emailLabel.setText(userProfileInfo.getEmail());
                Picasso.get().load(userProfileInfo.Image_Url).into(profileImageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        mBtnMyPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToMyposts = new Intent(Profile.this,CurrentUserPosts.class);
                startActivity(intToMyposts);

            }
        });


        mBtnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intToMyBookings = new Intent(Profile.this,CurrentUserBookings.class);
                startActivity(intToMyBookings);

            }
        });



        mBtnPackageBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intToMyPackageBookings = new Intent(Profile.this,CurrentUserPackageBookings.class);
                startActivity(intToMyPackageBookings);

            }
        });







    }



}