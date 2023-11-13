package assignmentbyamitrtestright.example.testrightassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.sign_in_button).setOnClickListener{
            var email = findViewById<EditText>(R.id.login_email).text.toString()
            var password = findViewById<EditText>(R.id.login_passs).text.toString()

            if(email.isNotBlank() && password.isNotBlank()){
                signInFunction(email,password)
            }else{
                Toast.makeText(applicationContext, "Not filled properly", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun signInFunction(email: String, password: String) {
        findViewById<ProgressBar>(R.id.progress_bar_login).visibility = View.VISIBLE
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    var intent:Intent = Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    // You can navigate to the next activity or perform other actions here
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    findViewById<ProgressBar>(R.id.progress_bar_login).visibility = View.GONE
                }
            }
    }
}