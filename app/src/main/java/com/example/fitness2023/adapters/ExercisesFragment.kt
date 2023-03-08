package com.example.fitness2023.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitness2023.adapters.ExerciseAdapter
import com.example.fitness2023.databinding.ExerciseListFragmentBinding
import com.example.fitness2023.databinding.ExercisesBinding
import com.example.fitness2023.utils.FragmentManager
import com.example.fitness2023.utils.MainViewModel


class ExercisesFragment : Fragment() { //15
    private lateinit var binding: ExercisesBinding
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

        model.mutableListExercise.observe(viewLifecycleOwner){
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExercisesFragment()
    }
}