package com.group.changemyview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.google.firebase.database.*
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

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
    var prevQuestion: TextView? = null
    var nextQuestion: TextView? = null
    var mQuestion: TextView? = null
    var questionNumber = 0
    val user = FirebaseAuth.getInstance().currentUser
    var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        prevQuestion = findViewById<TextView>(R.id.prevButton)
        nextQuestion = findViewById<TextView>(R.id.nextButton)

        mQuestion = findViewById(R.id.question)
        mQuestion!!.setText(questionList[0])

        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseDatabase.getInstance().getReference("users")
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    if(snapshot.key == user) {
                        username = snapshot.child("username").value.toString()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        chooseAnswer()

        // set on click listeners for previous and next button
        prevQuestion!!.setOnClickListener {
            if (questionNumber == 0) {
                Toast.makeText(
                    applicationContext,
                    "You are at the first question", Toast.LENGTH_LONG
                ).show()
            } else {
                questionNumber--
                chooseAnswer()
            }
        }

        nextQuestion!!.setOnClickListener {
            if (questionNumber == questionList.size - 1) {
                Toast.makeText(applicationContext,
                    "You are at the last question", Toast.LENGTH_LONG).show()
            } else {
                questionNumber++
                chooseAnswer()
            }
        }
    }

    private fun chooseAnswer() {
        mQuestion!!.setText(questionList[questionNumber])
        yesBtn = findViewById(R.id.yesButton)
        noBtn = findViewById(R.id.noButton)
        yesBtn!!.setOnClickListener {
            if (questionNumber == questionList.size - 1) {
                val currentQuestion = questionList[questionNumber]
                val currentUser = user!!.uid
                val currentEmail = user!!.email.toString()
                val ref = FirebaseDatabase.getInstance().getReference("/Questions/$currentQuestion/$currentUser")
                val answer = Answer(currentEmail,"Yes", username)
                ref.setValue(answer)

                startActivity(Intent(this@QuestionActivity, HomeActivity::class.java))
            } else {
                val currentQuestion = questionList[questionNumber]
                val currentUser = user!!.uid
                val currentEmail = user!!.email.toString()
                val ref = FirebaseDatabase.getInstance().getReference("/Questions/$currentQuestion/$currentUser")
                val answer = Answer(currentEmail,"Yes",username)
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
                val answer = Answer(currentEmail,"No",username)
                ref.setValue(answer)

                startActivity(Intent(this@QuestionActivity, HomeActivity::class.java))
            } else {
                val currentQuestion = questionList[questionNumber]
                val currentUser = user!!.uid
                val currentEmail = user!!.email.toString()
                val ref = FirebaseDatabase.getInstance().getReference("/Questions/$currentQuestion/$currentUser")
                val answer = Answer(currentEmail,"No",username)
                ref.setValue(answer)
                updateQuestion()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.report_user -> {
                val intent = Intent(applicationContext, ReportActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            R.id.sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.report_user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun updateQuestion() {
        questionNumber++
        mQuestion!!.setText(questionList[questionNumber])
    }
}
class Answer(val email:String, val answer:String, val username:String)