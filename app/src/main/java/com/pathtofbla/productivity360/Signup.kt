package com.pathtofbla.productivity360

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signup()
        switchToLogin()
        animations()
    }

    private fun signup() {
        signupButton.setOnClickListener {
            val email = emailTextViewSignup.text.toString()
            val password = passwordTextViewSignup.text.toString()

            if (email == "") {
                emailTextInputLayoutSignup.error = "Please enter a valid email address"
                return@setOnClickListener
            } else {
                emailTextInputLayoutSignup.error = null
            }
            if (password == "") {
                passwordTextInputLayoutSignup.error = "Please enter a password"
                return@setOnClickListener
            } else {
                passwordTextInputLayoutSignup.error = null
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val uid = FirebaseAuth.getInstance().uid.toString()
                    val userRef = FirebaseFirestore.getInstance().collection("users").document(uid)
                    val user = User(uid)

                    userRef.set(user).addOnSuccessListener {
                        val switchToMain = Intent(this, MainActivity::class.java)
                        switchToMain.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(switchToMain)
                    }
                }
                .addOnFailureListener {
                    when (it.message) {
                        "The email address is badly formatted." -> {
                            emailTextInputLayoutSignup.error = "Please enter a valid email address"
                        }
                        "The email address is already in use by another account." -> {
                            emailTextInputLayoutSignup.error = "The email is taken. Try Another one"
                        }
                        "The given password is invalid. [ Password should be at least 6 characters ]" -> {
                            passwordTextInputLayoutSignup.error =
                                "Password should be at least 6 characters"
                        }
                        else -> {
                            Toast.makeText(
                                this,
                                it.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
        }
    }

    private fun switchToLogin() {
        accountCheckSignup.setOnClickListener {
            val switchToLogin = Intent(this, Login::class.java)
            switchToLogin.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(switchToLogin)
        }
    }

    private fun animations() {
        val containerTranslateY = ObjectAnimator.ofFloat(linearLayoutSignup, "translationY", -100f)
        val containerTranslateAlpha = ObjectAnimator.ofFloat(linearLayoutSignup, "alpha", 0f, 1f)
        AnimatorSet().apply {
            playTogether(
                containerTranslateY,
                containerTranslateAlpha
            )
            duration = 1200
            start()
        }
    }
}