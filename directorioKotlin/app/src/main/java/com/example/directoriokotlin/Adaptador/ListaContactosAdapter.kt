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
import kotlinx.android.synthetic.main.item_list.view.*


class ListaContactosAdapter : RecyclerView.Adapter<ListaContactosAdapter.ViewHolder>() {
    var dataset: MutableList<Contacto> = ArrayList()
    lateinit var  context: Context
    lateinit var mOnContactoListener: onContactoListener

    fun ListaContactosAdapter(itemList: List<Contacto>, context: Context, onContactoListener: onContactoListener){
        this.context = context
        this.mOnContactoListener = onContactoListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaContactosAdapter.ViewHolder {
        val mInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var view: View = mInflater.inflate(R.layout.item_list,parent,false)
        return ListaContactosAdapter.ViewHolder(view,mOnContactoListener)
    }
    override fun onBindViewHolder(holder: ListaContactosAdapter.ViewHolder, position: Int) {

        holder.bindData(dataset.get(position))

    }
    fun adicionarListaContacto(contacto: Contacto){
        dataset.add(contacto)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View, onContactoListener: onContactoListener) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val nombre = view.findViewById(R.id.nombreUsuario) as TextView
        val apellidos = view.findViewById(R.id.numeroUsuario) as TextView
        var onContactoListener = onContactoListener

        fun bindData(contacto: Contacto){
            nombre.text = contacto.nombre + " " + contacto.apellido
            apellidos.text = contacto.telefono
            itemView.setOnClickListener(this)
            this.onContactoListener = onContactoListener
        }
        override fun onClick(v: View){
            onContactoListener.onContactoClick(adapterPosition)
        }

    }
     interface onContactoListener{
        fun onContactoClick(position: Int)

    }
    override fun getItemCount(): Int{
        return dataset.size
    }

}
