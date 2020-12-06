package com.lucky.todo_list_app.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucky.todo_list_app.data.model.Todo
import com.lucky.todo_list_app.databinding.TodoRowLayoutBinding

class ListAdaptor : RecyclerView.Adapter<ListAdaptor.ListViewHolder>() {

     var todoItems = emptyList<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            holder.bind(todoItems[position])
    }

    override fun getItemCount(): Int = todoItems.size

    fun addTodoItems(items: List<Todo>) {
        val todoListDiffUtil = TodoListDiffUtil(items,todoItems)
        val todoDiffResult =  DiffUtil.calculateDiff(todoListDiffUtil)
        todoItems = items
        todoDiffResult.dispatchUpdatesTo(this)
    }

    class ListViewHolder(private val binding: TodoRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return ListViewHolder(TodoRowLayoutBinding.inflate(layoutInflater, parent, false))
            }
        }
    }
}