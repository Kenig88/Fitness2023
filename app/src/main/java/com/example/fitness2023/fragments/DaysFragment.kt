package com.example.fitness2023.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness2023.R
import com.example.fitness2023.adapters.DayModel
import com.example.fitness2023.adapters.DaysAdapter
import com.example.fitness2023.adapters.ExerciseModel
import com.example.fitness2023.databinding.FragmentDaysBinding
import com.example.fitness2023.utils.FragmentManager
import com.example.fitness2023.utils.MainViewModel


class DaysFragment : Fragment(), DaysAdapter.Listener {
    private var ab: ActionBar? = null //19.3.0
    private lateinit var binding: FragmentDaysBinding
    private val model: MainViewModel by activityViewModels() //10.1.1

    override fun onCreateView( //создаёт все view
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //3 //запускается когда все view уже созданы
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar //19.3.1
        ab?.title = getString(R.string.days_list) //19.3.2
        initRcView()
    }

    private fun initRcView() = with(binding){ //2
        val adapter = DaysAdapter(this@DaysFragment)
        rcView.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcView.adapter = adapter
        adapter.submitList(fillDaysArray())
    }

    private fun fillDaysArray(): ArrayList<DayModel>{ //1
        val tempArray = ArrayList<DayModel>()
        resources.getStringArray(R.array.day_exercises).forEach{
            tempArray.add(DayModel(it, false))
        }
        return tempArray
    }

    private fun fillExerciseList(day: DayModel){ //9
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach(){
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(ExerciseModel(exerciseArray[0], exerciseArray[1], exerciseArray[2]))
        }
        model.mutableListExercise.value = tempList //10.2.1
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(day: DayModel) { //5
        fillExerciseList(day) //9.1
        FragmentManager.setFragment(ExerciseListFragment.newInstance(), activity as AppCompatActivity)
    }
}