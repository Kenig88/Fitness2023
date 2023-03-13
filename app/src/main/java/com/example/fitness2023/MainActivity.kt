package com.example.fitness2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.fitness2023.fragments.DaysFragment
import com.example.fitness2023.utils.FragmentManager
import com.example.fitness2023.utils.MainViewModel

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModels() //20.3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model.preferences = getSharedPreferences("main", MODE_PRIVATE) //20.4
        FragmentManager.setFragment(DaysFragment.newInstance(), this)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() { //7
        if(FragmentManager.currentFragment is DaysFragment) super.onBackPressed()
        else FragmentManager.setFragment(DaysFragment.newInstance(), this)
    }
}