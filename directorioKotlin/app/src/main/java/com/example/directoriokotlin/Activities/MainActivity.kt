package com.example.directoriokotlin.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import com.example.directoriokotlin.Adaptador.ListaContactosAdapter
import com.example.directoriokotlin.Database.AppDatabase
import com.example.directoriokotlin.Modelos.Contacto
import com.example.directoriokotlin.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ListaContactosAdapter.OnContactoListener {
    var listaContactos = emptyList<Contacto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var mRecyclerView: RecyclerView
        var mAdapter = ListaContactosAdapter(listaContactos.toMutableList(),this, this)
        val database = AppDatabase.getDatabase(this)

        mRecyclerView = findViewById(R.id.recyclerContactos)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        database.contactos().getContactos().observe(this, Observer {
            listaContactos = it
            mAdapter.ListaContactosAdapter(listaContactos.toMutableList(),this, this)
            mRecyclerView.adapter = mAdapter
        })

        btnCrearContacto.setOnClickListener{
            val intent = Intent(this, ContactoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onContactoClick(position: Int) {
        val intent = Intent(this, ContactoActivity::class.java)
        intent.putExtra("contacto",listaContactos[position])
        startActivity(intent)
    }

    override fun onDeleteclick(contacto: Contacto) {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Eliminar Contacto")
            .setMessage("¿Estás seguro de que deceas eliminar este contacto?")
            .setPositiveButton(
                "Yes"
            ) { dialog, which ->
                CoroutineScope(Dispatchers.IO).launch {
                   var database = AppDatabase.getDatabase(applicationContext)
                    database.contactos().borrarcontacto(contacto)
                }

            }
            .setNegativeButton("No", null)
            .show()

    }

}