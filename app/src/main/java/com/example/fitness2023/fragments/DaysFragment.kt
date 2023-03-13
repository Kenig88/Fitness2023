package com.example.fitness2023.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness2023.R
import com.example.fitness2023.adapters.DayModel
import com.example.fitness2023.adapters.DaysAdapter
import com.example.fitness2023.adapters.ExerciseModel
import com.example.fitness2023.databinding.FragmentDaysBinding
import com.example.fitness2023.utils.DialogManager
import com.example.fitness2023.utils.FragmentManager
import com.example.fitness2023.utils.MainViewModel


class DaysFragment : Fragment(), DaysAdapter.Listener {
    private lateinit var adapter: DaysAdapter //24.3
    private var ab: ActionBar? = null //19.3.0
    private lateinit var binding: FragmentDaysBinding
    private val model: MainViewModel by activityViewModels() //10.1.1

    override fun onCreate(savedInstanceState: Bundle?) { //24.1
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView( //создаёт все view
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //3 //запускается когда все view уже созданы
        super.onViewCreated(view, savedInstanceState)
        model.currentDay = 0 //22.4
        ab = (activity as AppCompatActivity).supportActionBar //19.3.1
        ab?.title = getString(R.string.days_list) //19.3.2
        initRcView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) { //24
        return inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //24.2
        if(item.itemId == R.id.reset){

            DialogManager.showDialog(activity as AppCompatActivity,  //25.1
                R.string.reset_all_days,
                object:DialogManager.Listener{
                    override fun onClick() {
                        model.preferences?.edit()?.clear()?.apply()
                        adapter.submitList(fillDaysArray())
                    }
                }
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRcView() = with(binding){ //2
        adapter = DaysAdapter(this@DaysFragment)
        rcView.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcView.adapter = adapter
        adapter.submitList(fillDaysArray())
    }

    private fun fillDaysArray(): ArrayList<DayModel>{ //1
        val tempArray = ArrayList<DayModel>()
        var daysDoneCounter = 0
        resources.getStringArray(R.array.day_exercises).forEach{
            model.currentDay++ //21.1
            val exCounter = it.split(",").size //21.2
            tempArray.add(DayModel(it, 0, model.getExerciseCount() == exCounter)) //20.6
        }
        binding.pBar.max = tempArray.size
        tempArray.forEach{
            if(it.isDone) daysDoneCounter++
        }
        updateRestDaysUi(tempArray.size - daysDoneCounter, tempArray.size)
        return tempArray
    }
    private fun updateRestDaysUi(restDays: Int, days: Int) = with(binding){ //23
        pBar.progress = days - restDays
    }

    private fun fillExerciseList(day: DayModel){ //9
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach(){
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(ExerciseModel(exerciseArray[0], exerciseArray[1], false, exerciseArray[2]))
        }
        model.mutableListExercise.value = tempList //10.2.1
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(day: DayModel) { //5
        if(!day.isDone){ //25.2
        fillExerciseList(day) //9.1
        model.currentDay = day.dayNumber //20.8
        FragmentManager.setFragment(ExerciseListFragment.newInstance(), activity as AppCompatActivity)
        } else { //25.3
            DialogManager.showDialog(
                activity as AppCompatActivity,
                R.string.reset_one_day,
                object: DialogManager.Listener{
                    override fun onClick() {
                        model.savePreferences(day.dayNumber.toString(), 0)
                        fillExerciseList(day)
                        model.currentDay = day.dayNumber
                        FragmentManager.setFragment(ExerciseListFragment.newInstance(),
                            activity as AppCompatActivity)
                    }
                }
            )
        }
    }
}