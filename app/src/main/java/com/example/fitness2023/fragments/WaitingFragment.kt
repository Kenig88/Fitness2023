package com.example.fitness2023.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.fitness2023.R
import com.example.fitness2023.databinding.WaitingFragmentBinding
import com.example.fitness2023.utils.FragmentManager
import com.example.fitness2023.utils.TimeUtils

const val COUNT_DOWN_TIME = 6000L //13
class WaitingFragment : Fragment() {
    private lateinit var binding: WaitingFragmentBinding
    private lateinit var timer : CountDownTimer
    private var ab: ActionBar? = null //19.2.0

    override fun onCreateView( //создаёт все view
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WaitingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //запускается когда все view уже созданы
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar //19.2.1
        ab?.title = getString(R.string.get_ready) //19.2.2
        binding.pBar.max = COUNT_DOWN_TIME.toInt() //13.2
        startTimer() //14.4
    }

    private fun startTimer() = with(binding){ //13.1
        timer = object : CountDownTimer(COUNT_DOWN_TIME, 1){
            override fun onTick(restTime: Long) {
                tvTimer.text = TimeUtils.getTime(restTime) //14.1
                pBar.progress = restTime.toInt() //13.3
            }
            override fun onFinish() {
                FragmentManager.setFragment(ExercisesFragment.newInstance(), //15.0.1
                    activity as AppCompatActivity)
            }
        }.start()
    }

    override fun onDetach() { //13.4
        super.onDetach()
        timer.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = WaitingFragment()
    }
}