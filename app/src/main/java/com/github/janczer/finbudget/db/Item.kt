package com.github.janczer.finbudget.db

class Item {
    var id: Int = 0
    var amount: Int = 0

    constructor(id: Int, amount: Int) {
        this.id = id
        this.amount = amount
    }

    constructor(amount: Int) {
        this.amount = amount
    }
}