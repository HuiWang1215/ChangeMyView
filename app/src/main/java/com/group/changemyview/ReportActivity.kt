package com.group.changemyview

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.graphics.Color.parseColor



class ReportActivity : AppCompatActivity() {

    var radioGroup : RadioGroup? = null
    var otherBtn : RadioButton? = null
    var reasonEditText : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val button = findViewById<Button>(R.id.report)
        radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        otherBtn = findViewById<RadioButton>(R.id.other)
        reasonEditText = findViewById<EditText>(R.id.reasons)
        reasonEditText!!.visibility = View.INVISIBLE

        val topLayout = findViewById<LinearLayout>(R.id.linearLayoutContainer)

        // resize top image
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val matchesBitmap = BitmapFactory.decodeResource(resources,
            R.drawable.matches_background)
        val matchesScaledBitmap = Bitmap.createScaledBitmap(matchesBitmap, width,
            height/4, false)
        val savedQuestionsDrawable = BitmapDrawable(resources, matchesScaledBitmap)
        topLayout!!.background = savedQuestionsDrawable


        button.setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            val db_users = FirebaseDatabase.getInstance().getReference("users")
            var uid = ""
            db_users.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var userFound = false
                    for (user in dataSnapshot.children) {
                        if (user.child("username").value == username) {
                            userFound = true
                            uid = user.key as String
                            break
                        }
                    }
                    if (!userFound) {
                        Toast.makeText(applicationContext,
                            "The user you entered does not exist.", Toast.LENGTH_SHORT).show()
                    }
                    else {

                        val builder = AlertDialog.Builder(this@ReportActivity)

                        builder.setTitle("Report User")

                        builder.setMessage("Are you want to report $username?")

                        builder.setPositiveButton("YES"){dialog, which ->
                            Toast.makeText(applicationContext,"$username has been reported.",Toast.LENGTH_SHORT).show()
                            val value = dataSnapshot.child(uid).child("report").value as Long
                            (dataSnapshot.child(uid).child("report").ref).setValue(value + 1.toLong())
                            finish()
                        }

                        builder.setNegativeButton("No"){dialog,which -> }

                        // Display a neutral button on alert dialog
                        builder.setNeutralButton("Cancel"){_,_ ->
                            Toast.makeText(applicationContext,"Cancelled report",Toast.LENGTH_SHORT).show()
                        }

                        // Finally, make the alert dialog using builder
                        val dialog: AlertDialog = builder.create()

                        // Display the alert dialog on app interface
                        dialog.show()

                        // Get the alert dialog buttons reference
                        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        val neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)

                        // Change the alert dialog buttons text and background color
                        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"))
                        negativeButton.setTextColor(Color.parseColor("#FFFF0400"))
                        neutralButton.setTextColor(Color.parseColor("#FF1B5AAC"))
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

        // set visibility to reasons edit text
        radioGroup!!.setOnCheckedChangeListener { group, checkedId ->
            val selectedId = radioGroup!!.checkedRadioButtonId

            if (selectedId == otherBtn!!.id) {
                reasonEditText!!.visibility = View.VISIBLE
            }
            else {
                reasonEditText!!.visibility = View.INVISIBLE
            }
        }

    }


}