package userPackage

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.healthtracker.R

///user class
class User(val id: Int = -1,
           val username: String,
           val password: String,
           val fullName: String,
           val email:String)
{

}