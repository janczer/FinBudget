package com.github.janczer.finbudget.db

class Category {
    var id: Int = 0
    var name: String = ""

    constructor(id: Int, name: String) {
        this.id = id
        this.name = name
    }

    constructor(name: String) {
        this.name = name
    }
}