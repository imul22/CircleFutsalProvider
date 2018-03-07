package com.kodecircle.circlefutsalprovider;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.kodecircle.circlefutsalprovider.core.FirestoreUtil;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Map<String, Object> data = new HashMap<>();
        data.put(FirestoreUtil.futsalFieldKey.name, true);

        FirestoreUtil.getFutsalField().add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext() ,"sukses", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext() ,"Register gagal", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
