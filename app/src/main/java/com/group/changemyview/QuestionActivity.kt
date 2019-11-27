package com.group.changemyview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.database.*
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {
    var questionList = arrayListOf(
        "Should USA have a single payer healthcare system?",
        "Should standardized testing be abolished?",
        "Is patriotism ultimately destructive to international relations?",
        "Should abortion be available to all women?",
        "Does social media do more harm than good?",
        "Is organic farming the future of agriculture?",
        "Should zoos be banned?",
        "Is artificial intelligence dangerous?",
        "Should children be exposed to technology?",
        "Should all cars be electric?")

    var yesBtn: Button? = null
    var noBtn: Button? = null
    var mQuestion: TextView? = null
    var questionNumber = 0
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        mQuestion = findViewById(R.id.question)
        mQuestion!!.setText(questionList[0])
        yesBtn = findViewById(R.id.yesButton)
        noBtn = findViewById(R.id.noButton)

        yesBtn!!.setOnClickListener {
            if (questionNumber == questionList.size - 1) {
                val currentQuestion = questionList[questionNumber]
                val currentUser = user!!.uid
                val currentEmail = user!!.email.toString()
                val ref = FirebaseDatabase.getInstance().getReference("/Questions/$currentQuestion/$currentUser")
                val answer = Answer(currentEmail,"Yes")
                ref.setValue(answer)

                startActivity(Intent(this@QuestionActivity, HomeActivity::class.java))
            } else {
                val currentQuestion = questionList[questionNumber]
                val currentUser = user!!.uid
                val currentEmail = user!!.email.toString()
                val ref = FirebaseDatabase.getInstance().getReference("/Questions/$currentQuestion/$currentUser")
                val answer = Answer(currentEmail,"Yes")
                ref.setValue(answer)
                updateQuestion()
            }
        }
        noBtn!!.setOnClickListener {
            if (questionNumber == questionList.size - 1) {
                val currentQuestion = questionList[questionNumber]
                val currentUser = user!!.uid
                val currentEmail = user!!.email.toString()
                val ref = FirebaseDatabase.getInstance().getReference("/Questions/$currentQuestion/$currentUser")
                val answer = Answer(currentEmail,"No")
                ref.setValue(answer)

                startActivity(Intent(this@QuestionActivity, HomeActivity::class.java))
            } else {
                val currentQuestion = questionList[questionNumber]
                val currentUser = user!!.uid
                val currentEmail = user!!.email.toString()
                val ref = FirebaseDatabase.getInstance().getReference("/Questions/$currentQuestion/$currentUser")
                val answer = Answer(currentEmail,"No")
                ref.setValue(answer)
                updateQuestion()
            }
        }
    }
    private fun updateQuestion() {
        questionNumber++
        mQuestion!!.setText(questionList[questionNumber])
    }
}
class Answer(val email:String, val answer:String)