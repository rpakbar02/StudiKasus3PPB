package com.example.bapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bapplication.databinding.ItemMahasiswaBinding

class MahasiswaAdapter : ListAdapter<Mahasiswa, MahasiswaAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: ItemMahasiswaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mahasiswa: Mahasiswa) {
            binding.tvNama.text = mahasiswa.nama
            binding.tvNim.text = "NIM: ${mahasiswa.nim} | Prodi: ${mahasiswa.prodi}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMahasiswaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Mahasiswa>() {
        override fun areItemsTheSame(oldItem: Mahasiswa, newItem: Mahasiswa) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Mahasiswa, newItem: Mahasiswa) =
            oldItem == newItem
    }
}