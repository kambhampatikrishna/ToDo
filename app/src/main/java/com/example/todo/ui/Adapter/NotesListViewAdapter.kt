package com.example.todo.ui.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.Notes
import kotlin.random.Random

class NotesListViewAdapter(context: Context,  notesData: ArrayList<Notes>,  listener: NotesClickListener) :
    RecyclerView.Adapter<NotesListViewAdapter.NotesViewHolder>() {


    var context : Context
    var notesData : ArrayList<Notes>
    var listener : NotesClickListener
    init {
        this.context = context
        this.notesData = notesData
        this.listener = listener
    }
    class NotesViewHolder(var context: Context, view: View) : RecyclerView.ViewHolder(view) {
        var cardView = view.findViewById<CardView>(R.id.itemCardView)
        var title = view.findViewById<TextView>(R.id.tv_title)
        var noteDescription = view.findViewById<TextView>(R.id.tv_note)
        var dateAndTime = view.findViewById<TextView>(R.id.tv_date_and_time)
        fun bind(note: Notes) {
            title.text = note.title
            noteDescription.text = note.notes

            dateAndTime.text = String.format(context.getString(R.string.date_and_time, note.date, note.time))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        context,
        LayoutInflater.from(parent.context).inflate(R.layout.list_tem_notes, parent, false)
    )

    override fun getItemCount(): Int {
        return notesData.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.bind(notesData[position])
        holder.cardView.setCardBackgroundColor(
            holder.cardView.resources.getColor(
                getRandomColor(),
                null
            )
        )
        holder.cardView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("notes", notesData[position])
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_editFragment, bundle)
        }

        holder.cardView.setOnLongClickListener {
            listener.onLongClickListener(holder.cardView, notesData[position])
            true
        }

    }


    interface NotesClickListener {

        fun onLongClickListener(cardView: CardView, note: Notes)
    }

    fun getRandomColor(): Int {
        val colorsList = ArrayList<Int>()
        colorsList.add(R.color.NoteColor1)
        colorsList.add(R.color.NoteColor2)
        colorsList.add(R.color.NoteColor3)
        colorsList.add(R.color.NoteColor4)
        colorsList.add(R.color.NoteColor5)
        colorsList.add(R.color.NoteColor6)
        val seed = System.currentTimeMillis().toInt()
        val randomColorId = Random(seed).nextInt(colorsList.size)
        return colorsList[randomColorId]
    }

    fun filterQueryData(filteredNotesData: ArrayList<Notes>) {
        notesData = filteredNotesData
        notifyDataSetChanged()
    }

}