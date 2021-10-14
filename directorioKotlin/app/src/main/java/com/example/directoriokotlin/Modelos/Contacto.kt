package com.example.directoriokotlin.Modelos

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "Contactos")
class Contacto(
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val eMail: String,
    @PrimaryKey(autoGenerate = true)
    val idContacto: Int = 0
    ) :  Serializable