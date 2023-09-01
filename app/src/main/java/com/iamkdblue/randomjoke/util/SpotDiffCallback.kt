package com.iamkdblue.randomjoke.util

import androidx.recyclerview.widget.DiffUtil
import com.iamkdblue.randomjoke.model.Joke

class SpotDiffCallback(
        private val old: List<Joke>,
        private val new: List<Joke>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].id == new[newPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}
