package com.adeolaadesipe.singlechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    EditText registeredName, registeredNumber, registeredEmail;
    AppCompatButton registerBtn;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://singlechatapp-a1057-default-rtdb.firebaseio.com");

        registeredName = findViewById(R.id.userRegisteredNameID);
        registeredEmail = findViewById(R.id.userRegisteredEmailID);
        registeredNumber = findViewById(R.id.userRegisteredNumberID);
        registerBtn = findViewById(R.id.registerBtnID);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = registeredName.getText().toString();
                String email = registeredEmail.getText().toString();
                String number = registeredNumber.getText().toString();

                if (name.isEmpty() || email.isEmpty() || number.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "All field required", Toast.LENGTH_SHORT).show();
                }

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("users").hasChild(number)){
                            Toast.makeText(RegisterActivity.this, "Number already exits", Toast.LENGTH_SHORT).show();
                        }else {
                            reference.child("users").child(number).child("email").setValue(email);
                            reference.child("users").child(number).child("name").setValue(name);
                            Toast.makeText(RegisterActivity.this, "User successfully registered", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("number", number);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);

                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}