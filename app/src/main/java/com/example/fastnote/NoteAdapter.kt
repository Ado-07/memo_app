package com.example.fastnote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(private val notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content
        holder.dateTextView.text = formatDate(note.timestamp)
    }

    override fun getItemCount() = notes.size

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.JAPAN)
        return sdf.format(Date(timestamp))
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.text_view_title)
        val contentTextView: TextView = itemView.findViewById(R.id.text_view_content)
        val dateTextView: TextView = itemView.findViewById(R.id.text_view_date)
    }
}
