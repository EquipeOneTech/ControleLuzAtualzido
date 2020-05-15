package com.equipeonetech.apptest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.equipeonetech.apptest.conexion.Conexion;
import com.equipeonetech.apptest.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {
    private Button btEntrar, btCadastro;
    private EditText edtEmail, edtPassword;
    private Context context = this;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_screen);

    /**Iniciando componentes*/
    initComponents();

    /**Evento ao clicar no botao 'Sign in'*/
    eventClickEntrar();

    /**Evento ao clicar no botao 'Create Account*/
    eventClickCreateAccount();

    //gambiarra
        edtEmail.setText("teste1@email.com");
        edtPassword.setText("12345678");

    }

    private void eventClickCreateAccount() {
        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastroScreen = new Intent(context, AccountRegisterScreen.class);
                startActivity(cadastroScreen);
            }
        });
    }

    private void initComponents() {
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btEntrar = (Button)findViewById(R.id.btLogin);
        btCadastro = (Button)findViewById(R.id.btCadastro);
    }

    private void eventClickEntrar(){
        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString();

                if(email.isEmpty() && password.isEmpty()){
                    edtEmail.setError("Email é obrigatório.");
                    edtPassword.setError("Password é obrigatório.");
                }else {
                    login(email, password);
                }

            }
        });
    }

    private void login(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent caculateScreen = new Intent(context, CalculateScreen.class);
                            startActivity(caculateScreen);
                            Utils.mensagemSucessoLogin(context);
                        }else{
                            Utils.messageDynamic(context, "Email ou senha invalido");
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
