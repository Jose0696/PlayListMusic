package com.example.ftmusicapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ftmusicapp.data.model.SongByPlayList
import com.example.ftmusicapp.databinding.ItemSongByPlaylistBinding

class SongByPlayListAdapter(
    private val onRemoveClick: (SongByPlayList) -> Unit
) : RecyclerView.Adapter<SongByPlayListAdapter.SongByPlayListViewHolder>() {

    private val songs = mutableListOf<SongByPlayList>()

    fun submitList(newList: List<SongByPlayList>) {
        songs.clear()
        songs.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongByPlayListViewHolder {
        val binding = ItemSongByPlaylistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SongByPlayListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongByPlayListViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int = songs.size

    inner class SongByPlayListViewHolder(
        private val binding: ItemSongByPlaylistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(song: SongByPlayList) {
            binding.tvSongTitle.text = song.title
            binding.tvSongArtist.text = song.artist

            val album = song.album ?: "No album"
            val genre = song.genre ?: "No genre"
            val lapseTime = song.lapseTime ?: "N/A"

            binding.tvSongDetails.text = "$album • $genre • $lapseTime"

            binding.btnRemoveSong.setOnClickListener {
                onRemoveClick(song)
            }
        }
    }
}