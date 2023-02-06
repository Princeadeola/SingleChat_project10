package com.adeolaadesipe.singlechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private String name;
    private String number;
    private String email;
    private RecyclerView messageRV;
    ImageView userProfilePicture;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageRV = findViewById(R.id.messagesRVID);
        userProfilePicture = findViewById(R.id.userProfilePictureID);
        reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://singlechatapp-a1057-default-rtdb.firebaseio.com");

        number = getIntent().getStringExtra("number");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");

        messageRV.setHasFixedSize(true);
        messageRV.setLayoutManager(new LinearLayoutManager(this));

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        dialog.show();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String profilePicsUrl = snapshot.child("users").child(number).child("profile_pic").getValue(String.class);

                if (!profilePicsUrl.isEmpty()){
                    Picasso.get().load(profilePicsUrl).into(userProfilePicture);
                    dialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
    }
}