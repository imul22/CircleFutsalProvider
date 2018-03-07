package com.kodecircle.circlefutsalprovider;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.LayoutInflater;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kodecircle.circlefutsalprovider.core.FirestoreUtil;
import com.kodecircle.circlefutsalprovider.model.User;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.kodecircle.circlefutsalprovider.core.FirestoreUtil.*;


import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    //Test
    Button btnSignIn,btnRegister;

    FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LayoutInflater inflater;
    RelativeLayout rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //init firebase
        auth = FirebaseAuth.getInstance();


        //init view
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnSignIn =  (Button)findViewById(R.id.btnSignIn);
        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);


        //Event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showRegisterDialog();

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });


    }

    private void showLoginDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SIGNIN");
        dialog.setMessage("Please Insert Email to Sign in");


        LayoutInflater inflater = LayoutInflater.from(this);
        View signin_layout = inflater.inflate(R.layout.activity_sign_in,null);

        final MaterialEditText edtEmail = signin_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword = signin_layout.findViewById(R.id.edtPassword);

        dialog.setView(signin_layout);

        dialog.setPositiveButton("SIGNIN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        //cek validation
                        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                            Snackbar.make(rootLayout, "Please insert email address", Snackbar.LENGTH_SHORT)
                                    .show();
                            return;
                        }

                        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                            Snackbar.make(rootLayout, "Please insert Password", Snackbar.LENGTH_SHORT)
                                    .show();
                            return;
                        }

                        if (edtPassword.getText().toString().length() < 6) {
                            Snackbar.make(rootLayout, "Password to short", Snackbar.LENGTH_SHORT)
                                    .show();
                            return;
                        }

                        auth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    startActivity(new Intent(MainActivity.this,home.class));
                                    finish();
                                }
                            }) .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(rootLayout,"Failed"+e.getMessage(),Snackbar.LENGTH_SHORT)
                                        .show();

                            }
                        });
                    }


                });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });



        dialog.show();

    }

    private void showRegisterDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER");
        dialog.setMessage("Silahkan isi Form Untuk Registrasi");


        LayoutInflater inflater = LayoutInflater.from(this);
        View registrasi_layout = inflater.inflate(R.layout.activity_register,null);

        final MaterialEditText edtName = registrasi_layout.findViewById(R.id.edtName);
        final MaterialEditText edtEmail = registrasi_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword = registrasi_layout.findViewById(R.id.edtPassword);

        dialog.setView(registrasi_layout);

        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                //cek validation
                if (TextUtils.isEmpty(edtName.getText().toString()))
                {
                    Snackbar.make(rootLayout,"Please insert UserName ",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(edtEmail.getText().toString()))
                {
                    Snackbar.make(rootLayout,"Please insert email address",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                    Snackbar.make(rootLayout, "Please insert Password", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                if (edtPassword.getText().toString().length() < 6)
                {
                    Snackbar.make(rootLayout,"Password to short",Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                //registrasi new user
                auth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //save user
                                Map<String,Object> user = new HashMap<>();
                                user.put(futsalFieldKey.name,edtName.getText().toString());
                                user.put(futsalFieldKey.email,edtEmail.getText().toString());
                                user.put(futsalFieldKey.password,edtPassword.getText().toString());

                                //user email to key
                                FirestoreUtil.getFutsalField().document(authResult.getUser().getUid())
                                        .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar.make(rootLayout,"Registrasi Sukses",Snackbar.LENGTH_SHORT)
                                                .show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(rootLayout,"Registrasi Gagal",Snackbar.LENGTH_SHORT)
                                                .show();
                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(rootLayout,"Failed"+e.getMessage(),Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        });
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });

        dialog.show();
    }
}
