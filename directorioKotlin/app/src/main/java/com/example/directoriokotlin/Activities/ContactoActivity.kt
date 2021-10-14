package com.example.directoriokotlin.Activities

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.red
import com.example.directoriokotlin.Database.AppDatabase
import com.example.directoriokotlin.Modelos.Contacto
import com.example.directoriokotlin.R
import kotlinx.android.synthetic.main.activity_contacto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContactoActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto)

        val database = AppDatabase.getDatabase(this)
        var Contacto1 = Contacto("","","","")
        val comando: String =  intent.getStringExtra("accion") as String//Recibir nombre de accion
        val def = "Añadir"

        if(comando.equals("Editar")){
            val Contacto = intent.getSerializableExtra("contacto") as Contacto //Recibir objeto Contacto
            txtNombre.setText(Contacto.nombre)
            txtApellidos.setText(Contacto.nombre)
            txtTelefono.setText(Contacto.nombre)
            txtEmail.setText(Contacto.nombre)
            txtComando.setText(comando)
            btnEjecutarAccion.setText(comando)
            Contacto1 = Contacto
        }else{
            txtComando.setText(def)
            btnEjecutarAccion.setText(def)
        }

        btnEjecutarAccion.setOnClickListener {
            if(comando.equals(def) && !txtNombre.text.toString().equals("") && !txtApellidos.text.toString().equals("") && !txtTelefono.text.toString().equals("") && !txtEmail.text.toString().equals("")){
                val nombre = txtNombre.text.toString()
                val apellidos = txtApellidos.text.toString()
                val telefono = txtTelefono.text.toString()
                val email = txtEmail.text.toString()

                val contactoNuevo = Contacto(nombre,apellidos,telefono,email)
                CoroutineScope(Dispatchers.IO).launch {
                    database.contactos().insertarContactos(contactoNuevo)

                    this@ContactoActivity.finish()
                }
            }else{
                 AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Campos Vacios")
                    .setMessage("Por favor, rellena todos los campos")
                    .setPositiveButton("OK",null)
                    .show()
            }
            if(comando == "Editar" && !txtNombre.text.toString().equals("") && !txtApellidos.text.toString().equals("") && !txtTelefono.text.toString().equals("") && !txtEmail.text.toString().equals("")){
                val nombre = txtNombre.text.toString()
                val apellidos = txtApellidos.text.toString()
                val telefono = txtTelefono.text.toString()
                val email = txtEmail.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    database.contactos().actualizarContacto(Contacto1)
                    this@ContactoActivity.finish()
                }
            }else{
                AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Campos Vacios")
                    .setMessage("Por favor, rellena todos los campos")
                    .setPositiveButton("OK",null)
                    .show()
            }
        }




    }
}