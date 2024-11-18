package com.example.unsecuredseguros.exception

class NotFoundException (message: String)
    : Exception("Not found exception (404). $message") {

}