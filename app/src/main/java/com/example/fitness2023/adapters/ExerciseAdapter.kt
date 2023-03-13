package com.example.fitness2023.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness2023.R
import com.example.fitness2023.databinding.ExerciseListItemBinding
import pl.droidsonroids.gif.GifDrawable

class ExerciseAdapter() : ListAdapter<ExerciseModel, //11
        ExerciseAdapter.ExerciseHolder>(Comparator()){

    class ExerciseHolder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = ExerciseListItemBinding.bind(view)

        fun setData(exercise: ExerciseModel) = with(binding){
            tvName.text = exercise.name
            tvCount.text = exercise.time
            chB.isChecked = exercise.isDone //21.1
            imEx.setImageDrawable(GifDrawable(root.context.assets, exercise.gif)) //12
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_list_item, parent, false)
        return ExerciseHolder(view)
    }
    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class Comparator : DiffUtil.ItemCallback<ExerciseModel>(){
        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }
    }
}
