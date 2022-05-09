import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.smarthomeapp.HomeFragment
import com.example.smarthomeapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class test : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigatin_view)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.drawer_nav_graph,
                HomeFragment()
            ).commit()
        }
    }

    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.btnHome -> selectedFragment =
                    HomeFragment()

            }
            supportFragmentManager.beginTransaction().replace(
                R.id.drawer_nav_graph,
                selectedFragment!!
            ).commit()
            true
        }
}