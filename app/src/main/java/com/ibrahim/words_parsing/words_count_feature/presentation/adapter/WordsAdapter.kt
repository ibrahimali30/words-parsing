package com.ibrahim.words_parsing.words_count_feature.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.words_parsing.R
import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_words_item.view.*

class WordsAdapter(val data: ArrayList<WordsUiModel> = java.util.ArrayList()) :
    RecyclerView.Adapter<WordsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_words_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setList(list: List<WordsUiModel>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    class ViewHolder constructor(var view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("CheckResult")
        fun bind(model: WordsUiModel) {
            view.tvWordTitle.text = model.word
            view.tvWordCount.text = model.count.toString()

        }
    }
}