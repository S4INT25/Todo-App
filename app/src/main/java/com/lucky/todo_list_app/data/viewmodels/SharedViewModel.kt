package com.lucky.todo_list_app.data.viewmodels

import android.app.Application
import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lucky.todo_list_app.data.model.Priority
import com.lucky.todo_list_app.data.model.Todo

class SharedViewModel(context: Application) : AndroidViewModel(context) {

    val emptyDatabase:MutableLiveData<Boolean> =  MutableLiveData(false)

    fun checkIfDataBaseEmpty(todo:List<Todo>){
        emptyDatabase.value =  todo.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {

            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(Color.RED)
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(Color.YELLOW)
                }
                2 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(Color.GREEN)
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }


    }

    fun parsePriorityColor(priority: String): Priority {
        return when (priority) {
            "High" -> {
                Priority.HIGH
            }
            "Medium" -> {
                Priority.MEDIUM
            }
            "Low" -> {
                Priority.LOW
            }
            else -> {
                Priority.MEDIUM
            }
        }
    }



}