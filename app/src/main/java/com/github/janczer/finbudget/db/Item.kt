package com.github.janczer.finbudget.db

class Item {
    var id: Int = 0
    var amount: Int = 0
    var idcategory: Int = 0
    var date: String = ""

    constructor(id: Int, amount: Int, idcategory: Int, date: String) {
        this.id = id
        this.amount = amount
        this.idcategory = idcategory
        this.date = date
    }

    constructor(amount: Int) {
        this.amount = amount
    }
}
