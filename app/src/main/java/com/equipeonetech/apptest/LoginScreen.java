package com.equipeonetech.apptest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.equipeonetech.apptest.conexion.Conexion;
import com.equipeonetech.apptest.messages_screen.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {
    private Button btEntrar, btCadastro;
    private EditText edtEmail, edtPassword;
    private Context context = this;
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
                    Toast.makeText(context,"Em testes", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}
