package com.group.changemyview

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.util.DisplayMetrics
import android.R.attr.bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log


class HomeActivity : AppCompatActivity() {

    internal var savedQuestionsButton: Button? = null
    internal var answerQuestionsButton: Button? = null
    internal var matchesButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // create background images for Buttons
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        savedQuestionsButton = findViewById(R.id.savedQuestionsButton)
        answerQuestionsButton = findViewById(R.id.answerQuestionsButton)
        matchesButton = findViewById(R.id.matchesButton)


        val answerQuestionsBitmap = BitmapFactory.decodeResource(resources,
            R.drawable.answer_questions_background)
        val answerQuestionsScaledBitmap = Bitmap.createScaledBitmap(answerQuestionsBitmap, width,
            height/3, false)
        val answerQuestionsDrawable = BitmapDrawable(resources, answerQuestionsScaledBitmap)
        answerQuestionsButton!!.background = answerQuestionsDrawable

        val savedQuestionsBitmap = BitmapFactory.decodeResource(resources,
            R.drawable.saved_questions_background)
        val savedQuestionsScaledBitmap = Bitmap.createScaledBitmap(savedQuestionsBitmap, width,
            height/3, false)
        val savedQuestionsDrawable = BitmapDrawable(resources, savedQuestionsScaledBitmap)
        savedQuestionsButton!!.background = savedQuestionsDrawable

        val matchesBitmap = BitmapFactory.decodeResource(resources,
            R.drawable.matches_background)
        Log.i(TAG, matchesBitmap.toString())
        val matchesScaledBitmap = Bitmap.createScaledBitmap(matchesBitmap, width,
            height/3, false)
        val matchesDrawable = BitmapDrawable(resources, matchesScaledBitmap)
        matchesButton!!.background = matchesDrawable


        // activities to start when buttons are clicked
        answerQuestionsButton!!.setOnClickListener {
            val intent = Intent(this@HomeActivity, QuestionActivity::class.java)
            startActivity(intent)
        }

        // create on click listener for saved questions and get matches buttons 
    }

    companion object {
        private val TAG = "FinalProject-ChangeMyView"
    }
}