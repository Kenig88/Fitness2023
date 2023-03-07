package com.example.fitness2023.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fitness2023.databinding.WaitingFragmentBinding

const val COUNT_DOWN_TIME = 6000L //13
class WaitingFragment : Fragment() {
    private lateinit var binding: WaitingFragmentBinding
    private lateinit var timer : CountDownTimer

    override fun onCreateView( //создаёт все view
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WaitingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //запускается когда все view уже созданы
        super.onViewCreated(view, savedInstanceState)
        binding.pBar.max = COUNT_DOWN_TIME.toInt() //13.2
        startTimer()
    }

    private fun startTimer() = with(binding){ //13.1
        timer = object : CountDownTimer(COUNT_DOWN_TIME, 1){
            override fun onTick(restTime: Long) {
                pBar.progress = restTime.toInt() //13.3
            }
            override fun onFinish() {

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