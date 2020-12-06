package com.lucky.todo_list_app.ui.fragments.addtodo

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lucky.todo_list_app.R
import com.lucky.todo_list_app.data.model.Todo
import com.lucky.todo_list_app.data.viewmodels.SharedViewModel
import com.lucky.todo_list_app.data.viewmodels.TodoViewModel
import com.lucky.todo_list_app.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val todoViewModel by viewModels<TodoViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        binding.spinner.onItemSelectedListener = sharedViewModel.listener
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.MI_add -> {
                addTodo()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addTodo() {
        val title = binding.OTFAddTitle.editText?.text
        val priority = binding.spinner.selectedItem.toString()
        val description = binding.OTFAddDescription.editText?.text

        if (title.isNullOrBlank() and description.isNullOrBlank()) {
            binding.OTFAddDescription.error = "*required"
            binding.OTFAddTitle.error = "*required"
            binding.spinner.isFocusable = true
            return
        }
        try {
            todoViewModel.addTodo(
                Todo(
                    0,
                    title.toString(),
                    sharedViewModel.parsePriorityColor(priority),
                    description.toString()
                )
            )
            Toast.makeText(requireContext(), "Added!!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } catch (e: Exception) {

            Log.d(TAG, "addTodo: ${e.printStackTrace()}")
            Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
        }


    }



    companion object {
        private const val TAG = "AddFragment"
    }

}