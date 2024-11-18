package com.example.unsecuredseguros.controller

import com.example.unsecuredseguros.exception.ValidationException
import com.example.unsecuredseguros.model.Seguro
import com.example.unsecuredseguros.service.SeguroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/seguros")
class SeguroController {

    @Autowired
    lateinit var seguroService: SeguroService;

    // GET SEGURO POR ID
    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: String
    ) : ResponseEntity<Seguro>? {

        // Validacion minima
        if (id.isBlank() || id == "a") {
            // Voy a lanzar una excepcion
            throw ValidationException("El id no puede estar vacío")
        }

        seguroService.getById(id)


        return null

    }




}