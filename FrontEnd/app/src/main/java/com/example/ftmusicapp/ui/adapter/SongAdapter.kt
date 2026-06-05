package com.example.ftmusicapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ftmusicapp.data.model.Song
import com.example.ftmusicapp.databinding.ItemSongCatalogBinding

class SongAdapter(
    private val onAddClick: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private val songs = mutableListOf<Song>()

    fun submitList(newList: List<Song>) {
        songs.clear()
        songs.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongCatalogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int = songs.size

    inner class SongViewHolder(
        private val binding: ItemSongCatalogBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song) {
            binding.tvSongTitle.text = song.title
            binding.tvSongArtist.text = song.artist

            val album = song.album ?: "No album"
            val genre = song.genre ?: "No genre"
            val lapseTime = song.lapseTime ?: "N/A"

            binding.tvSongDetails.text = "$album • $genre • $lapseTime"

            binding.btnAddSong.setOnClickListener {
                onAddClick(song)
            }
        }
    }
}