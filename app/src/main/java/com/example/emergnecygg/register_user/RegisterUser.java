package com.example.emergnecygg.register_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emergnecygg.R;
import com.example.emergnecygg.modules.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import dmax.dialog.SpotsDialog;

public class RegisterUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        final EditText txtNombre = (EditText)findViewById(R.id.txtNombre);
        final EditText txtPaterno = (EditText)findViewById(R.id.txtPaterno);
        final EditText txtMaterno = (EditText)findViewById(R.id.txtMaterno);
        final EditText txtEdad = (EditText)findViewById(R.id.txtEdad);
        final EditText txtTelefono = (EditText)findViewById(R.id.txtTelefono);
        final EditText txtCorreo = (EditText)findViewById(R.id.txtCorreo);
        final EditText txtPassword = (EditText)findViewById(R.id.txtPassword);


        Button btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog dialogo = new AlertDialog(RegisterUser.this);
                dialogo.show();
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtCorreo.getText().toString(),txtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User usuario = new User(txtNombre.getText().toString(),txtPaterno.getText().toString(),txtMaterno.getText().toString(),txtTelefono.getText().toString(),txtPassword.getText().toString(),
                                    Integer.parseInt(txtEdad.getText().toString()), task.getResult().getUser().getUid(),txtPassword.getText().toString());
                            FirebaseFirestore.getInstance().collection("Usuarios").document(task.getResult().getUser().getUid()).set(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(),"Usuario Registrado!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}
