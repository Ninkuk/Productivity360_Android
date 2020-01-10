package com.pathtofbla.productivity360

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        changeActivityToDashboard()
        login()
        forgotPassword()
        switchToSignup()
        animations()
    }

    private fun changeActivityToDashboard() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val switchToDashboard = Intent(this, MainActivity::class.java)
            switchToDashboard.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(switchToDashboard)
        }
    }

    private fun login() {
        loginButton.setOnClickListener {
            val email = emailTextView.text.toString()
            val password = passwordTextView.text.toString()

            if(email.isEmpty()) {
                emailTextInputLayout.error = "Please enter a valid email address"
                return@setOnClickListener
            }else {
                emailTextInputLayout.error = null
            }
            if(password.isEmpty()) {
                passwordTextInputLayout.error = "Please enter a password"
                return@setOnClickListener
            }else {
                passwordTextInputLayout.error = null
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener{
                    emailTextView.text?.clear()
                    passwordTextView.text?.clear()

                    val switchToMain = Intent(this, MainActivity::class.java)
                    switchToMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(switchToMain)
                }
                .addOnFailureListener {
                    when (it.message) {
                        "The email address is badly formatted." -> {
                            emailTextInputLayout.error = "Please enter a valid email address"
                        }
                        "There is no user record corresponding to this identifier. The user may have been deleted." -> {
                            Toast.makeText(
                                this,
                                "Your email or password is incorrect",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        "The password is invalid or the user does not have a password." -> {
                            Toast.makeText(
                                this,
                                "Your email or password is incorrect",
                                Toast.LENGTH_LONG
                            ).show()
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

    private fun forgotPassword() {
        forgotPassword.setOnClickListener {
            val email = emailTextView.text.toString()

            if (email.isEmpty()) {
                emailTextInputLayout.error = "Please enter a valid email address"
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "Check your email for instructions to change the password",
                        Toast.LENGTH_LONG
                    ).show()
                }.addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Enter a valid email address",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    private fun switchToSignup() {
        accountCheck.setOnClickListener {
            val switchToSignup = Intent(this, Signup::class.java)
            switchToSignup.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(switchToSignup)
        }
    }

    private fun animations() {
        val containerTranslateY = ObjectAnimator.ofFloat(linearLayout, "translationY", -100f)
        val containerTranslateAlpha = ObjectAnimator.ofFloat(linearLayout, "alpha", 0f, 1f)
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
