package com.group.changemyview

class Match {
    var question: String = ""
    var answer: String = ""
    var email: String = ""
    var to: String = ""

    constructor() {}

    constructor(question: String, answer: String, email : String, to : String) {
        this.question = question
        this.answer = answer
        this.email = email
        this.to = to
    }
}