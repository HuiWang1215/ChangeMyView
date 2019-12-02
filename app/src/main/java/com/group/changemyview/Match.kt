package com.group.changemyview

class Match {
    var question: String = ""
    var answer: String = ""
    var email: String = ""

    constructor() {}

    constructor(question: String, answer: String, email : String) {
        this.question = question
        this.answer = answer
        this.email = email
    }
}