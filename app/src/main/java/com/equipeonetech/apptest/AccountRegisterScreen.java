package com.equipeonetech.apptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.equipeonetech.apptest.conexion.Conexion;
import com.equipeonetech.apptest.messages_screen.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AccountRegisterScreen extends AppCompatActivity {
    private Button btVoltar, btCadastrar;
    private EditText edtEmailCad, edtPasswordCad;
    private Context context = this;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register_screen);
        /**Iniciando componentes*/
        initComponents();

        /**Evento criar usuario*/
        eventClickCadastro();

        /**Evento voltar*/
        eventClickVoltar();
    }

    private void eventClickCadastro() {
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailCad.getText().toString().trim();
                String password = edtPasswordCad.getText().toString();
                if(email.isEmpty() && password.isEmpty()){
                    edtEmailCad.setError("Email é obrigatório.");
                    edtPasswordCad.setError("Password é obrigatório.");
                }else {
                    createUser(email, password);
                }
            }
        });
    }

    private void eventClickVoltar() {
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginScreen = new Intent(context, AccountRegisterScreen.class);
                startActivity(loginScreen);
                finish();
            }
        });
    }

    private void initComponents() {
        btCadastrar = (Button)findViewById(R.id.btCriar);
        btVoltar = (Button)findViewById(R.id.btVoltarCad);
        edtEmailCad = (EditText)findViewById(R.id.edtEmailCad);
        edtPasswordCad = (EditText)findViewById(R.id.edtPasswordCad);
    }

    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(AccountRegisterScreen.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Utils.mensagemSucessoLogin(context);
                            final Intent calculateScreen = new Intent(context, CalculateScreen.class);
                            startActivity(calculateScreen);
                            finish();
                        } else {
                            Utils.mensagemErrorLogin(context);
                        }
                    }
                });

    }

        @Override
        protected void onStart() {
            super.onStart();
            firebaseAuth = Conexion.getFirebaseAuth();
        }
}
