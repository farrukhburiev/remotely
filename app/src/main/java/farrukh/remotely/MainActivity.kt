package farrukh.remotely

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import farrukh.remotely.databinding.ActivityMainBinding
import farrukh.remotely.ui.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(R.id.main, HomeFragment()).setReorderingAllowed(true).commit()
    }
}