package space.korelskiy.korelskiymovieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import space.korelskiy.korelskiymovieapp.R
import space.korelskiy.korelskiymovieapp.ui.movie_det.SingleMovie

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener{
            val intent = Intent(this, SingleMovie::class.java)
            intent.putExtra("id", 635302)
            this.startActivity(intent)
        }
    }
}