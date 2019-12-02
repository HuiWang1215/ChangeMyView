package com.group.changemyview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.google.firebase.database.*
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
//
class UpdateQuestionActivity : AppCompatActivity() {
//
//    var yesBtn: Button? = null
//    var noBtn: Button? = null
//    var mQuestion: TextView? = null
//    var questionNumber = 0
//    val user = FirebaseAuth.getInstance().currentUser
//    var username = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_question)
//        mQuestion = findViewById(R.id.question)
//
//        val questionText = intent.getStringExtra("Question text") as String
//        mQuestion!!.setText(questionText)
//
//        val db = FirebaseDatabase.getInstance().getReference("users")
//        db.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    username = snapshot.child("username").value.toString()
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
//
//        chooseAnswer()
//    }
//
//    private fun chooseAnswer() {
////        mQuestion!!.setText(questionList[questionNumber])
//        yesBtn = findViewById(R.id.yesButton)
//        noBtn = findViewById(R.id.noButton)
////        yesBtn!!.setOnClickListener {
////            if (questionNumber == questionList.size - 1) {
////                val currentQuestion = questionList[questionNumber]
////                val currentUser = user!!.uid
////                val currentEmail = user!!.email.toString()
////                val ref = FirebaseDatabase.getInstance().getReference("/Questions/$currentQuestion/$currentUser")
////                val answer = Answer(currentEmail,"Yes", username)
////                ref.setValue(answer)
////
////                startActivity(Intent(this@QuestionActivity, HomeActivity::class.java))
////            } else {
////                val currentQuestion = questionList[questionNumber]
////                val currentUser = user!!.uid
////                val currentEmail = user!!.email.toString()
////                val ref = FirebaseDatabase.getInstance().getReference("/Questions/$currentQuestion/$currentUser")
////                val answer = Answer(currentEmail,"Yes",username)
////                ref.setValue(answer)
////                updateQuestion()
////            }
////        }
////        noBtn!!.setOnClickListener {
////            if (questionNumber == questionList.size - 1) {
////                val currentQuestion = questionList[questionNumber]
////                val currentUser = user!!.uid
////                val currentEmail = user!!.email.toString()
////                val ref = FirebaseDatabase.getInstance().getReference("/Questions/$currentQuestion/$currentUser")
////                val answer = Answer(currentEmail,"No",username)
////                ref.setValue(answer)
////
////                startActivity(Intent(this@QuestionActivity, HomeActivity::class.java))
////            } else {
////                val currentQuestion = questionList[questionNumber]
////                val currentUser = user!!.uid
////                val currentEmail = user!!.email.toString()
////                val ref = FirebaseDatabase.getInstance().getReference("/Questions/$currentQuestion/$currentUser")
////                val answer = Answer(currentEmail,"No",username)
////                ref.setValue(answer)
////                updateQuestion()
////            }
////        }
//    }
//
}