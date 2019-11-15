package com.group.changemyview

class QuestionItem internal constructor(questionNum: Int, question: String, answer: Answer) {

    var mQuestionNum: Int? = questionNum
    var mQuestion: String? = question
    var mAnswer: Answer? = answer

    enum class Answer {
        YES, NO, NO_ANSWER
    }

    companion object {

        val ITEM_SEP = System.getProperty("line.separator")
    }

}
