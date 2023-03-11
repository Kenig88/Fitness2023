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
import com.example.fitness2023.adapters.ExerciseAdapter
import com.example.fitness2023.databinding.ExerciseListFragmentBinding
import com.example.fitness2023.utils.FragmentManager
import com.example.fitness2023.utils.MainViewModel


class ExerciseListFragment : Fragment() {
    private lateinit var binding: ExerciseListFragmentBinding
    private var ab: ActionBar? = null //19.1.0
    private lateinit var adapter: ExerciseAdapter
    private val model: MainViewModel by activityViewModels() //10.1.2

    override fun onCreateView( //создаёт все view
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //запускается когда все view уже созданы
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar //19.1.1
        ab?.title = getString(R.string.exercise_list) //19.1.2
        initRcView() //11.2
        model.mutableListExercise.observe(viewLifecycleOwner){
            adapter.submitList(it)
        } //10.2.2
    }

    private fun initRcView() = with(binding){ //11.1
        adapter = ExerciseAdapter()
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        bStart.setOnClickListener{ //14.3
            FragmentManager.setFragment(WaitingFragment.newInstance(), activity as AppCompatActivity)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExerciseListFragment()
    }
}