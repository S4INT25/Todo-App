package com.lucky.todo_list_app.ui.fragments.todolist

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lucky.todo_list_app.R
import com.lucky.todo_list_app.adaptor.ListAdaptor
import com.lucky.todo_list_app.data.model.Todo
import com.lucky.todo_list_app.data.viewmodels.SharedViewModel
import com.lucky.todo_list_app.data.viewmodels.TodoViewModel
import com.lucky.todo_list_app.databinding.FragmentListBinding
import com.lucky.todo_list_app.util.hideKeyboard
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val adaptor by lazy { ListAdaptor() }
    private val viewModel by viewModels<TodoViewModel>()
    private val sharedViewModel by viewModels<SharedViewModel>()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel
        init()
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        hideKeyboard(requireActivity())
        return binding.root
    }

    private fun init() {
        viewModel.getAllData().observe(viewLifecycleOwner, {
            sharedViewModel.checkIfDataBaseEmpty(it)
            adaptor.addTodoItems(it)
        })
        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.hasFixedSize()
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        swipeToDelete(binding.recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val deletedItem = adaptor.todoItems[viewHolder.adapterPosition]
                    //delete item
                    viewModel.deleteTodo(deletedItem)
                    adaptor.notifyItemRemoved(viewHolder.adapterPosition)
                    //undo delete
                    undoDelete(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
                }
            }
        ItemTouchHelper(swipeToDeleteCallback).apply {
            attachToRecyclerView(recyclerView)
        }
    }

    private fun undoDelete(view: View, deletedItem: Todo, position: Int) {
        Snackbar.make(view, "Deleted '${deletedItem.title}'", Snackbar.LENGTH_LONG).apply {
            setAction("Undo") {
                viewModel.addTodo(deletedItem)
            }
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        val search = menu.findItem(R.id.MI_search)
       (search.actionView as SearchView).apply {
            isSubmitButtonEnabled =  true
            setOnQueryTextListener(this@ListFragment)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchInDatabase(it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            searchInDatabase(it)
        }
        return true
    }

    private fun searchInDatabase(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner) {
            it?.let {
                adaptor.addTodoItems(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.MT_deleteAll -> {
                deleteAll()
            }
            R.id.low -> viewModel.orderByLow().observe(viewLifecycleOwner){adaptor.addTodoItems(it)}
            R.id.high -> viewModel.orderByHigh().observe(viewLifecycleOwner){adaptor.addTodoItems(it)}
        }
        return super.onOptionsItemSelected(item)
    }


    private fun deleteAll() {
        AlertDialog.Builder(requireContext()).apply {

            setTitle("Delete All")
            setMessage("Are you sure you want to delete All items? ")
            setPositiveButton("yes") { _, _ ->
                viewModel.deleteAll()
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            create()
            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        private const val TAG = "ListFragment"
    }
}