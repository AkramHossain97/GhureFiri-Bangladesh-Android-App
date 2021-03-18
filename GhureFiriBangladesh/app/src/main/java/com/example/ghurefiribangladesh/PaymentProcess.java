package com.example.ghurefiribangladesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apptakk.http_request.HttpRequest;
import com.apptakk.http_request.HttpRequestTask;
import com.apptakk.http_request.HttpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PaymentProcess extends AppCompatActivity {
    private String mDepartureDate = null;
    private String mTotal_Bill = null;
    private String mTime_SlotSelected = null;
    private String mTotalPassenger = null;
    private TextView PmTotalPassenger;
    private TextView PmDepartureDate;
    private TextView PmTotal_Bill;
    private TextView UserName;
    private EditText PhoneNumber;
    Button PaymentProcess;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabaseUser;
    int randomNumber;
    HttpURLConnection myURLConnection;
    String textmessage;
    String to;
    URL url = null;
    HttpURLConnection urlConnection = null;
    DatabaseReference Phonereference;
    FirebaseDatabase PhonerootNode;
    String NewPhoneNumber;





    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_process);
        mTotal_Bill=getIntent().getExtras().getString("Total_Bill");
        mDepartureDate=getIntent().getExtras().getString("DepartureDate");
        mTime_SlotSelected=getIntent().getExtras().getString("Time_SlotSelected");
        mTotalPassenger=getIntent().getExtras().getString("TotalPassenger");

        PmTotal_Bill=findViewById(R.id.TotalOrderPricePayment);
        PmTotalPassenger=findViewById(R.id.TotalPERSONSPayment);
        PmDepartureDate=findViewById(R.id.JourneyDatePayment);
        UserName=findViewById(R.id.UserName);
        PhoneNumber=findViewById(R.id.Phone_Number);
        PaymentProcess=findViewById(R.id.PayNowProcess);

        PmTotal_Bill.setText("Total Payable Amount : "+mTotal_Bill);
        PmTotalPassenger.setText("Total Passenger : "+mTotalPassenger);
        PmDepartureDate.setText("Departure Date : "+mDepartureDate);

        PhonerootNode= FirebaseDatabase.getInstance();
        Phonereference=PhonerootNode.getReference("users");


        mDatabaseUser= FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());


        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserProfileInfo userProfileInfo = snapshot.getValue(UserProfileInfo.class);
                UserName.setText(userProfileInfo.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        PaymentProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Random random = new Random();
                randomNumber=random.nextInt(999999);
                to=PhoneNumber.getText().toString();
                NewPhoneNumber=PhoneNumber.getText().toString();



                textmessage = "Your+verification+code+for+GhureFiri+Bangladesh+is+"+randomNumber+" ";




                new HttpRequestTask(
                        new HttpRequest("https://api.greenweb.com.bd/api.php?token=TokenHasBeenRemoved="+to+"&message="+textmessage, HttpRequest.POST, "{ \"some\": \"data\" }"),
                        new HttpRequest.Handler() {
                            @Override
                            public void response(HttpResponse response) {
                                if (response.code == 200) {
                                    Phonereference.child(user.getUid()).child("phone").setValue(NewPhoneNumber);
                                    Intent VerifyIntent = new Intent(PaymentProcess.this,PaymentVerification.class);
                                    VerifyIntent.putExtra("RandomNumberGen",randomNumber);
                                    VerifyIntent.putExtra("PhoneNumberGen",to);
                                    startActivity(VerifyIntent);
                                } else {
                                    Log.e(this.getClass().toString(), "Request unsuccessful: " + response);
                                }
                            }
                        }).execute();





            }
        });



    }
}

