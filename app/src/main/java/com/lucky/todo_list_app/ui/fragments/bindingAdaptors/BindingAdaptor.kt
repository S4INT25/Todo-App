package com.lucky.todo_list_app.ui.fragments.bindingAdaptors


import android.graphics.Color
import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lucky.todo_list_app.R
import com.lucky.todo_list_app.data.model.Priority
import com.lucky.todo_list_app.data.model.Todo
import com.lucky.todo_list_app.ui.fragments.todolist.ListFragmentDirections

class BindingAdaptor {

    companion object {
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view:View,emptyDatabase: MutableLiveData<Boolean>)
        {
            when(emptyDatabase.value)
            {
                true -> view.isVisible =  true
                false -> view.isVisible =  false
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(spinner: Spinner,priority: Priority)
        {
            when(priority)
            {
                Priority.HIGH -> spinner.setSelection(0)
                Priority.MEDIUM -> spinner.setSelection(1)
                Priority.LOW -> spinner.setSelection(2)
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView,priority: Priority)
        {
            when(priority){
                Priority.HIGH -> cardView.setCardBackgroundColor(Color.RED)
                Priority.MEDIUM -> cardView.setCardBackgroundColor(Color.YELLOW)
                Priority.LOW -> cardView.setCardBackgroundColor(Color.GREEN)
            }
        }

        @BindingAdapter("android:navigateToUpdateFragment")
        @JvmStatic
        fun navigateToUpdateFragment(cardView: CardView,todo: Todo)
        { cardView.setOnClickListener {
                    val action  = ListFragmentDirections.actionListFragmentToUpdateFragment(todo)
                    cardView.findNavController().navigate(action)

            }
        }
    }
}