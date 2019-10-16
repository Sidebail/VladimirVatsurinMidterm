package com.tfg.vladimirvatsurinmidterm

class Order{
    var id: String = "";
    var firstName: String? = null;
    var lastName: String? = null;
    var type: String? = null;
    var size: String? = null;
    var quantity: Int? = null;
    var total: Int? = null;

    constructor(){

    }

    constructor(id: String, firstName: String, lastName: String, type: String, size: String, quantity:Int, total: Int){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.size = size;
        this.quantity = quantity;
        this.total = total;
    }

    /**
    fun getId(): Int? {
        return this.id;
    }
    */
}