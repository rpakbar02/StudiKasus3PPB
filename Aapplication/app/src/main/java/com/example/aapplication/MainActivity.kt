package com.example.aapplication

import android.content.ContentValues
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inisialisasi View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan status di layar
        binding.tvStatus.text = "Gudang Data Mahasiswa Aktif\nMenunggu permintaan dari Consumer App..."

        // Tambahkan data contoh secara otomatis
        insertDummyData()
    }

    private fun insertDummyData() {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase

        // Menyiapkan data mahasiswa contoh
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_NAMA, "Budi Santoso")
            put(DatabaseHelper.COL_NIM, "220101001")
            put(DatabaseHelper.COL_PRODI, "Ilmu Komputer")
            put(DatabaseHelper.COL_IPK, 3.85f)
        }

        // Simpan ke database menggunakan parameterized insert
        val result = db.insert(DatabaseHelper.TABLE_MAHASISWA, null, values)

        if (result != -1L) {
            Toast.makeText(this, "Data contoh berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }
}