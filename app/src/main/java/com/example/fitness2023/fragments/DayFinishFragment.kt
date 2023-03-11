package com.example.fitness2023.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.fitness2023.R
import com.example.fitness2023.databinding.DayFinishBinding
import com.example.fitness2023.utils.FragmentManager
import pl.droidsonroids.gif.GifDrawable

class DayFinishFragment : Fragment() { //18
    private lateinit var binding: DayFinishBinding
    private var ab: ActionBar? = null //19.4.0

    override fun onCreateView( //создаёт все view
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DayFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //запускается когда все view уже созданы
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar //19.4.1
        ab?.title = getString(R.string.done_day_finish) //19.4.2
        binding.imMain.setImageDrawable(GifDrawable((activity as AppCompatActivity).assets,
            "congratulations-african.gif"))
        binding.btDone.setOnClickListener{
            FragmentManager.setFragment(DaysFragment.newInstance(), activity as AppCompatActivity)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DayFinishFragment()
    }
}