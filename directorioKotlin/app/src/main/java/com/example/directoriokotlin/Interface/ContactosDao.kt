package com.example.directoriokotlin.Interface

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.directoriokotlin.Modelos.Contacto

@Dao
interface ContactosDao {
    @Query("SELECT * FROM Contactos") //Obtener rows de tabla Contactos
    fun getContactos(): LiveData<List<Contacto>>

    @Query("SELECT * FROM Contactos WHERE idContacto = :id") //Obtener row Contacto por id
    fun get(id: Int): LiveData<Contacto>

    @Insert
    fun insertarContactos(vararg contactos : Contacto)

    @Update
    fun actualizarContacto(contacto : Contacto)

    @Delete
    fun borrarcontacto(contacto : Contacto)

}