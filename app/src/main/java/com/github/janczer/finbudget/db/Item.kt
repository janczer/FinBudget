package com.github.janczer.finbudget.db

import java.math.BigDecimal

class Item {
    var id: Int = 0
    var amount: BigDecimal = BigDecimal.ZERO
    var idcategory: Int = 0
    var date: String = ""

    constructor(id: Int, amount: BigDecimal, idcategory: Int, date: String) {
        this.id = id
        this.amount = amount
        this.idcategory = idcategory
        this.date = date
    }

    constructor(amount: BigDecimal) {
        this.amount = amount
    }
}
