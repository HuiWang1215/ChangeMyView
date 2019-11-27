package com.group.changemyview

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.security.KeyStore
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    internal var registerButton: Button? = null
    internal var photoButton: Button? = null
    internal var emailEditText: EditText? = null
    internal var passwordEditText: EditText? = null
    internal var usernameEditText: EditText? = null
    internal var haveAccountText: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registerButton = findViewById(R.id.register_button)
        photoButton = findViewById(R.id.photo_button)
        emailEditText = findViewById(R.id.email_edittext_register)
        passwordEditText = findViewById(R.id.password_edittext_register)
        usernameEditText = findViewById(R.id.username_edittext_register)
        haveAccountText = findViewById(R.id.already_have_account_textview)

        //var db = FirebaseDatabase.getInstance().getReference("Questions").child("Question1")
        //db.child("user3").setValue("yes")
        //db.child("user4").setValue("no")
        //db.addValueEventListener(object : ValueEventListener {
          //  override fun onDataChange(dataSnapshot: DataSnapshot) {
            //    for (snapshot in dataSnapshot.children) {
              //      var user = snapshot.getValue(String::class.java)
                //    Log.i("TAG", user)
                  //  Log.i("TAG", snapshot.key)
                //}

            //}
            //override fun onCancelled(databaseError: DatabaseError) {

            //}
        //})

        registerButton!!.setOnClickListener {
            register()
        }

        photoButton!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }

        haveAccountText!!.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    var photoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            photoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,photoUri)
            val bitmapDrawable = BitmapDrawable(this.resources,bitmap)
            photoButton!!.setBackground(bitmapDrawable)
        }
    }
    private fun register() {
        val email = emailEditText!!.text.toString()
        val password = passwordEditText!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter email！", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext, "Register successfully", Toast.LENGTH_LONG).show()
                    uploadPhoto()
                } else {
                    Toast.makeText(applicationContext, "Register failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun uploadPhoto() {
        if (photoUri == null){
            return
        }
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/image/$filename")

        ref.putFile(photoUri!!)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Successfully uploaded the photo", Toast.LENGTH_LONG).show()
                ref.downloadUrl.addOnSuccessListener {
                    saveUser(it.toString())
                }
            }
    }

    private fun saveUser(profileUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, usernameEditText!!.text.toString(), emailEditText!!.text.toString(),
            profileUrl)
        ref.setValue(user)
            .addOnSuccessListener {
                val intent = Intent(this@RegistrationActivity, HomeActivity::class.java)
                intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }
}

class User(val uid:String, val username:String, val email:String, val profileUrl:String)
data class Question(val username: String = "", val answer: String = "")