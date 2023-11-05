package com.example.todo.ui.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todo.R
import com.example.todo.ViewModel.NotesViewModel
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.model.Notes
import com.example.todo.ui.Adapter.NotesListViewAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), NotesListViewAdapter.NotesClickListener,
    PopupMenu.OnMenuItemClickListener {

    lateinit var binding: FragmentHomeBinding
    lateinit var selectedNote: Notes
    lateinit var notesListViewAdapter: NotesListViewAdapter
    val viewModel: NotesViewModel by viewModels()
    private var oldNotes = arrayListOf<Notes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            binding.notesRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
            oldNotes = notesList as ArrayList<Notes>
            notesListViewAdapter = NotesListViewAdapter(requireContext(),notesList as ArrayList<Notes>, this)
            binding.notesRecyclerView.adapter =
                notesListViewAdapter
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchNotes(newText!!.lowercase())
                return true
            }

        })


        binding.addNoteBtn.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNotesFragment2)
        }


        return binding.root
    }

    override fun onLongClickListener(cardView: CardView, note: Notes) {
        selectedNote = note
        showDeleteMenu(cardView)
    }

    fun showDeleteMenu(cardView: CardView) {
        val popupMenu = PopupMenu(requireContext(), cardView)
        popupMenu.setOnMenuItemClickListener(this@HomeFragment)
        popupMenu.inflate(R.menu.pop_up_menu)
        popupMenu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_note) {
            selectedNote.id?.let { viewModel.deleteNotes(it) }
            return true
        }
        return false
    }


    private fun searchNotes(query: String) {
        val newNotesList = arrayListOf<Notes>()
        if (oldNotes != null) {
            for (note in oldNotes) {
                if (note.title.lowercase().contains(query) ||
                    note.notes.lowercase().contains(query)
                ) {
                    newNotesList.add(note)
                }
            }
        }

        notesListViewAdapter.filterQueryData(newNotesList)

    }

}