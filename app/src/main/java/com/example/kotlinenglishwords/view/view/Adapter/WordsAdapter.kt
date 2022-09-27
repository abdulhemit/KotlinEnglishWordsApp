package com.example.kotlinenglishwords.view.view.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinenglishwords.databinding.RecyclerRowBinding
import com.example.kotlinenglishwords.view.view.Model.Words
import com.example.kotlinenglishwords.view.view.View.AddWordActivity

class WordsAdapter (val wordsList: List<Words>): RecyclerView.Adapter<WordsAdapter.WordsHolder>() {

    class WordsHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsHolder {
        val binding_row = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WordsHolder(binding_row)
    }

    override fun onBindViewHolder(holder: WordsHolder, position: Int) {
        holder.binding.idWord.text = wordsList.get(position).En_word
        holder.binding.idSentence.text = wordsList.get(position).En_Sentence
        holder.binding.count.text = wordsList.get(position).id.toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,AddWordActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("info","old")
            intent.putExtra("selectWord",wordsList.get(position)) as? Words
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return wordsList.size
    }
}