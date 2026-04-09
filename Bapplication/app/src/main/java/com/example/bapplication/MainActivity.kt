package com.example.bapplication

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MahasiswaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inisialisasi View Binding [cite: 1780-1781]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadDataFromProvider()
    }

    private fun setupRecyclerView() {
        adapter = MahasiswaAdapter()
        binding.rvMahasiswa.layoutManager = LinearLayoutManager(this)
        binding.rvMahasiswa.adapter = adapter
    }

    private fun loadDataFromProvider() {
        val list = mutableListOf<Mahasiswa>()
        // Alamat URI harus sama dengan Aapplication [cite: 334, 1119]
        val contentUri = Uri.parse("content://com.example.aapplication.provider/mahasiswa")

        contentResolver.query(contentUri, null, null, null, "nama ASC")?.use { cursor ->
            while (cursor.moveToNext()) {
                list.add(Mahasiswa(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    nama = cursor.getString(cursor.getColumnIndexOrThrow("nama")),
                    nim = cursor.getString(cursor.getColumnIndexOrThrow("nim")),
                    prodi = cursor.getString(cursor.getColumnIndexOrThrow("prodi")),
                    ipk = cursor.getFloat(cursor.getColumnIndexOrThrow("ipk"))
                ))
            }
        }
        adapter.submitList(list) // Update tampilan list [cite: 1927]
    }
}