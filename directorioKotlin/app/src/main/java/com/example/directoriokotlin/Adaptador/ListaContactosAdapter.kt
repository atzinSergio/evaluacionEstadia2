package com.example.directoriokotlin.Adaptador

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.directoriokotlin.Modelos.Contacto
import com.example.directoriokotlin.R
import kotlinx.android.synthetic.main.activity_contacto.view.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_list.view.*


class ListaContactosAdapter (
    private var dataset: MutableList<Contacto> = ArrayList(),
    private  var  context: Context,
    private var itemClickListener: OnContactoListener
): RecyclerView.Adapter<ListaContactosAdapter.ViewHolder>() {

    interface OnContactoListener{
        fun onContactoClick(position: Int)
        fun onDeleteclick(contacto: Contacto)

    }

    fun ListaContactosAdapter(itemList: MutableList<Contacto>, context: Context, itemClickListener: OnContactoListener){
        this.context = context
        this.dataset = itemList
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaContactosAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list,parent,false)
        )
    }
    override fun onBindViewHolder(holder: ListaContactosAdapter.ViewHolder, position: Int) {
        holder.bindData(dataset[position],position)
    }

     inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
         val nombre = itemView.findViewById(R.id.nombreUsuario) as TextView
         val apellidos = itemView.findViewById(R.id.numeroUsuario) as TextView

         fun bindData(contacto: Contacto, position: Int) {
             nombre.text = contacto.nombre + " " + contacto.apellido
             apellidos.text = contacto.telefono
             itemView.setOnClickListener{itemClickListener.onContactoClick(position)}
             itemView.btnBorrar.setOnClickListener{itemClickListener.onDeleteclick(contacto)}
         }

    }


    override fun getItemCount(): Int{
        return dataset.size
    }

}
