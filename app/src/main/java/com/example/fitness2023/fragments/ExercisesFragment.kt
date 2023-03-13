package com.example.fitness2023.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.fitness2023.R
import com.example.fitness2023.adapters.ExerciseModel
import com.example.fitness2023.databinding.ExercisesBinding
import com.example.fitness2023.utils.FragmentManager
import com.example.fitness2023.utils.MainViewModel
import com.example.fitness2023.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable


class ExercisesFragment : Fragment() { //15
    private var timer : CountDownTimer? = null //16.3
    private lateinit var binding: ExercisesBinding
    private var exerciseCounter = 0 //15.1
    private var exList: ArrayList<ExerciseModel>? = null //15.3
    private var ab: ActionBar? = null //19
    private var currentDay = 0 //22.2
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView( //создаёт все view
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //запускается когда все view уже созданы
        super.onViewCreated(view, savedInstanceState)
        currentDay = model.currentDay //22.3
        exerciseCounter = model.getExerciseCount()
        ab = (activity as AppCompatActivity).supportActionBar //19.0.1
        model.mutableListExercise.observe(viewLifecycleOwner){
            exList = it //15.4
            nextExercise() //15.6
        }
        binding.bNextEx.setOnClickListener{ //15.7
            nextExercise()
        }
    }

    private fun nextExercise(){ //15.2
        if(exerciseCounter < exList?.size!!){
            val ex = exList?.get(exerciseCounter++) ?: return
            showExercise(ex)
            setExerciseType(ex) //16.1
            showNextExercise() //17.1
        } else {
            exerciseCounter++ //22.1
            FragmentManager.setFragment(DayFinishFragment.newInstance(),
                activity as AppCompatActivity) //18.1
        }
    }

    private fun showExercise(exercise: ExerciseModel) = with(binding){ //15.5
        imMain.setImageDrawable(GifDrawable(root.context.assets, exercise.gif))
        tvNameEx.text = exercise.name
        val title = "$exerciseCounter / ${exList?.size}" //19.0.2
        ab?.title = title //19.0.3
    }

    private fun setExerciseType(exercise: ExerciseModel){ //16
        if(exercise.time.startsWith("x")){
            binding.tvTime.text = exercise.time
        } else {
            startTimer(exercise)
        }
    }

    private fun showNextExercise() = with(binding){ //17
        if(exerciseCounter < exList?.size!!){
            val ex = exList?.get(exerciseCounter) ?: return
            imNextEx.setImageDrawable(GifDrawable(root.context.assets, ex.gif))
            setTimeType(ex) //17.3
        } else {
            imNextEx.setImageDrawable(GifDrawable(root.context.assets, "congratulations-african.gif"))
            tvNextNameEx.text = getString(R.string.done)
        }
    }

    private fun setTimeType(ex: ExerciseModel) = with(binding){ //17.2
        if(ex.time.startsWith("x")){
            tvNextNameEx.text = ex.time
        } else {
            val name = ex.name + ": ${TimeUtils.getTime(ex.time.toLong() * 1000)}"
            tvNextNameEx.text = name
        }
    }

    private fun startTimer(exercise: ExerciseModel) = with(binding){ //16.2
        pBar.max = exercise.time.toInt() * 1000
        timer?.cancel()
        timer = object : CountDownTimer(exercise.time.toLong() * 1000, 1){
            override fun onTick(restTime: Long) {
                tvTime.text = TimeUtils.getTime(restTime)
                pBar.progress = restTime.toInt()
            }
            override fun onFinish() {
                nextExercise()
            }
        }.start()
    }

    override fun onDetach() { //16.4
        super.onDetach()
        model.savePreferences(currentDay.toString(), exerciseCounter - 1) //20.9
        timer?.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExercisesFragment()
    }
}