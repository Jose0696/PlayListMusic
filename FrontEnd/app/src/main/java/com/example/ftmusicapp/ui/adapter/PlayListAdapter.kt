package com.example.ftmusicapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ftmusicapp.data.model.PlayList
import com.example.ftmusicapp.databinding.ItemPlaylistBinding

class PlayListAdapter(
    private val onItemClick: (PlayList) -> Unit,
    private val onEditClick: (PlayList) -> Unit,
    private val onDeleteClick: (PlayList) -> Unit
) : RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder>() {

    private val playLists = mutableListOf<PlayList>()

    fun submitList(newList: List<PlayList>) {
        playLists.clear()
        playLists.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val binding = ItemPlaylistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PlayListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        holder.bind(playLists[position])
    }

    override fun getItemCount(): Int = playLists.size

    inner class PlayListViewHolder(
        private val binding: ItemPlaylistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(playList: PlayList) {
            binding.tvPlayListName.text = playList.namePlayList
            binding.tvPlayListInfo.text = playList.information ?: "No description"
            binding.tvPlayListDate.text = "Created: ${playList.dateCreate ?: "N/A"}"

            binding.containerPlayList.setOnClickListener {
                onItemClick(playList)
            }

            binding.btnEdit.setOnClickListener {
                onEditClick(playList)
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick(playList)
            }
        }
    }
}