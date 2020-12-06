package com.lucky.todo_list_app.ui.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lucky.todo_list_app.R
import com.lucky.todo_list_app.data.model.Todo
import com.lucky.todo_list_app.data.viewmodels.SharedViewModel
import com.lucky.todo_list_app.data.viewmodels.TodoViewModel
import com.lucky.todo_list_app.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val todoViewModel by viewModels<TodoViewModel>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UpdateFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args =  args
        binding.spinner.onItemSelectedListener = sharedViewModel.listener
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update -> {
                update()
            }
            R.id.MT_delete -> {
                delete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun delete() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Todo")
            setMessage("Are you sure you want to delete ${args.currentItem.title}? ")
            setPositiveButton("yes") { _, _ ->
                todoViewModel.deleteTodo(args.currentItem)
                Toast.makeText(requireContext(), "deleted", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            create()
            show()
        }
    }

    private fun update() {
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
            todoViewModel.updateTodo(
                Todo(
                    args.currentItem.id,
                    title.toString(),
                    sharedViewModel.parsePriorityColor(priority),
                    description.toString()
                )
            )
            Toast.makeText(requireContext(), "updated!!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } catch (e: Exception) {
            Log.d(TAG, "addTodo: ${e.printStackTrace()}")
            Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
        }


    }


    companion object {
        private const val TAG = "UpdateFragment"
    }
}