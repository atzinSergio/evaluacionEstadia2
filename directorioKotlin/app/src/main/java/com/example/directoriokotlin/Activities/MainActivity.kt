package com.example.directoriokotlin.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.directoriokotlin.Adaptador.ListaContactosAdapter
import com.example.directoriokotlin.Database.AppDatabase
import com.example.directoriokotlin.Modelos.Contacto
import com.example.directoriokotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ListaContactosAdapter.onContactoListener {
    var listaContactos = emptyList<Contacto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var mRecyclerView: RecyclerView
        val mAdapter: ListaContactosAdapter = ListaContactosAdapter()

        var database = AppDatabase.getDatabase(this)

        database.contactos().getContactos().observe(this, Observer {
            listaContactos = it
            mAdapter.adicionarListaContacto(listaContactos.last())
        })
        fun initRV(){
            mRecyclerView = findViewById(R.id.recyclerContactos) as RecyclerView
            mRecyclerView.setHasFixedSize(true)
            mRecyclerView.layoutManager = LinearLayoutManager(this)
            mAdapter.ListaContactosAdapter(listaContactos,this,this)
            mRecyclerView.adapter = mAdapter
        }
        initRV()


        btnCrearContacto.setOnClickListener{
            val intent = Intent(this, ContactoActivity::class.java)
            intent.putExtra("accion","Añadir")

            startActivity(intent)
        }
    }

    override fun onContactoClick(position: Int) {
        val intent = Intent(this, ContactoActivity::class.java)
        intent.putExtra("contacto",listaContactos[position])
        intent.putExtra("accion", "Editar")
        startActivity(intent)
    }

}