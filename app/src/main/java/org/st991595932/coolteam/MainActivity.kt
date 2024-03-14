package org.st991595932.coolteam

import org.st991595932.coolteam.R
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.st991595932.coolteam.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var searchButton: ImageButton

    lateinit var chatFragment: ChatFragment
    lateinit var profileFragment:ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding the xml file
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatFragment = ChatFragment()
        profileFragment= ProfileFragment()


        bottomNavigationView = binding.bottomNavigation
        searchButton = binding.mainSearchBtn

        searchButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, SearchUserActivity::class.java))
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.toString()) {
                "Chats" -> {
                    // Handle click on chat menu item
                    //supportFragmentManager.beginTransaction().replace(menuItem.itemId, chatFragment).commit()
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout, chatFragment).commit()

                    true
                }
                "Profile" -> {
                    // Handle click on profile menu item
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame_layout, profileFragment).commit()

                    true
                }
                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.menu_chat

    }


}