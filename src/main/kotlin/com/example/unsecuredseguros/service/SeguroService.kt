package com.example.unsecuredseguros.service

import com.example.unsecuredseguros.exception.NotFoundException
import com.example.unsecuredseguros.model.Seguro
import org.springframework.stereotype.Service

@Service
class SeguroService {

    fun getById(id: String) {

        // LOGICA NEGOCIO


        // BUSCO EL SEGURO POR EL ID
        val seguro: Seguro? = null; // EL SEGURO NO SE HA ENCONTRADO

        // DEBERIAMOS RESPONDER CON UN CODIGO DE ESTADO 404
        if(seguro == null) {
            throw NotFoundException("El seguro con id $id no ha sido encontrado")
        }

    }

}