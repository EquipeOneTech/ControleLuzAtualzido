package com.equipeonetech.apptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.equipeonetech.apptest.conexion.Conexion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileScreen extends AppCompatActivity {
    private Button btLogout;
    private TextView txtEmail, txtId;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        /**Inicia componentes*/
        initComponents();

        eventClickLogout();
    }

    private void eventClickLogout() {
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conexion.logout();
                finish();
            }
        });
    }

    private void initComponents() {
        btLogout = (Button)findViewById(R.id.btLogout);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtId = (TextView)findViewById(R.id.txtId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = Conexion.getFirebaseAuth();
        firebaseUser = Conexion.getFirebaseUser();
        verifyUser();
    }

    private void verifyUser() {
        if(firebaseUser == null){
            finish();
        }else{
            txtEmail.setText("Email: "+firebaseUser.getEmail());
            txtId.setText("ID: "+firebaseUser.getUid());
        }
    }
}
