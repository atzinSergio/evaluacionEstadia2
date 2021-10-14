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

        var idContacto: Int? = null

        if(!intent.hasExtra("contacto")){ //AÑADIR OBJETO
            txtComando.setText("Añadir")
            btnEjecutarAccion.setText("Añadir")

            btnEjecutarAccion.setOnClickListener {
                if(!txtNombre.text.toString().equals("") && !txtApellidos.text.toString().equals("") && !txtTelefono.text.toString().equals("") && !txtEmail.text.toString().equals("")){
                    val nombre = txtNombre.text.toString()
                    val apellidos = txtApellidos.text.toString()
                    val telefono = txtTelefono.text.toString()
                    val email = txtEmail.text.toString()
                    var contactoNuevo = Contacto(nombre,apellidos,telefono,email)
                    CoroutineScope(Dispatchers.IO).launch {
                        database.contactos().insertarContactos(contactoNuevo)
                        this@ContactoActivity.finish()
                    }
                }else { //Si existen campos vacios, no permite añadir registro
                    AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Campos Vacios")
                        .setMessage("Por favor, rellena todos los campos")
                        .setPositiveButton("OK",null)
                        .show()
                }
            } // Finaliza Listener Acción

        }else{
            val contacto = intent.getSerializableExtra("contacto") as Contacto //Recibir objeto Contacto

            //Rellenar Datos del Contacto
            txtNombre.setText(contacto.nombre)
            txtApellidos.setText(contacto.apellido)
            txtTelefono.setText(contacto.telefono)
            txtEmail.setText(contacto.eMail)
            txtComando.setText("Editar")
            btnEjecutarAccion.setText("Editar")
            //Fin Rellenar Datos del Contacto

            btnEjecutarAccion.setOnClickListener {
                if(!txtNombre.text.toString().equals("") && !txtApellidos.text.toString().equals("") && !txtTelefono.text.toString().equals("") && !txtEmail.text.toString().equals("")){
                    val nombre = txtNombre.text.toString()
                    val apellidos = txtApellidos.text.toString()
                    val telefono = txtTelefono.text.toString()
                    val email = txtEmail.text.toString()
                    idContacto = contacto.idContacto
                    var contactoNuevo = Contacto(nombre,apellidos,telefono,email)
                    contactoNuevo.idContacto = idContacto as Int
                    CoroutineScope(Dispatchers.IO).launch {
                        database.contactos().actualizarContacto(contactoNuevo)
                        this@ContactoActivity.finish()
                    }
                }else { //Si existen campos vacios, no permite actualizar registro
                    AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Campos Vacios")
                        .setMessage("Por favor, rellena todos los campos")
                        .setPositiveButton("OK",null)
                        .show()
                }
            } // Finaliza Listener Acción
        }
        backIcon.setOnClickListener{
            finish()
        }
    }

}
