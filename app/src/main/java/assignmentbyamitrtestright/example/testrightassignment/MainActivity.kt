package assignmentbyamitrtestright.example.testrightassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        findViewById<Button>(R.id.register_user_button).setOnClickListener {
            var name = findViewById<EditText>(R.id.register_user_name).text.toString()
            var email = findViewById<EditText>(R.id.register_user_email).text.toString()
            var address = findViewById<EditText>(R.id.register_user_address).text.toString()
            var phone = findViewById<EditText>(R.id.register_user_phone).text.toString()

            if (name.isNotBlank() && email.isNotBlank() && address.isNotBlank() && phone.isNotBlank()) {
                findViewById<ProgressBar>(R.id.progress_bar).visibility = View.VISIBLE
                updateIntheFirebase(name, email, address, phone)
            } else {
                Toast.makeText(applicationContext, "Not filled properly", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun updateIntheFirebase(name: String, email: String, address: String, phone: String) {
        val database = FirebaseDatabase.getInstance()
        val _key = database.getReference("data").push().key

        val user = User(name, email, address, phone)

        if (_key != null) {
            database.getReference("data").child(_key).setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext,"Done",Toast.LENGTH_SHORT).show()

                    findViewById<EditText>(R.id.register_user_name).text.clear()
                    findViewById<EditText>(R.id.register_user_email).text.clear()
                    findViewById<EditText>(R.id.register_user_address).text.clear()
                    findViewById<EditText>(R.id.register_user_phone).text.clear()

                    findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE

                }
                .addOnFailureListener{
                    Toast.makeText(applicationContext,it.toString(),Toast.LENGTH_SHORT).show()
                    findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
                }

        }


    }

    data class User(val userName: String, val email: String, val address: String, val phone: String)


}