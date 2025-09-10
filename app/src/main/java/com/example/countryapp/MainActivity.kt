package com.example.countryapp

import DialogExit
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.countryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var provinces: Array<String>

    private val spinnerCountry = arrayOf(
        "Indonesia", "United States", "United Kingdom", "Germany", "France",
        "Australia", "Japan", "China", "Brazil", "Canada"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menghubungkan binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan daftar provinsi dari resources
        provinces = resources.getStringArray(R.array.provinces)

        // Menyiapkan adapter untuk spinner negara
        val adapterCountry = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_item, spinnerCountry
        )
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCountry.adapter = adapterCountry

        // Menyiapkan adapter untuk spinner provinsi
        val adapterProvinces = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_item, provinces
        )
        adapterProvinces.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerProvinces.adapter = adapterProvinces

        binding.spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@MainActivity,
                        spinnerCountry[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val datePicker = findViewById<DatePicker>(R.id.datePicker)

        datePicker.init(
            datePicker.year,
            datePicker.month,
            datePicker.dayOfMonth
        ) { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
            Toast.makeText(this@MainActivity, selectedDate, Toast.LENGTH_SHORT).show()
        }


        val timePicker = findViewById<TimePicker>(R.id.timePicker)

        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            Toast.makeText(this@MainActivity, selectedTime, Toast.LENGTH_SHORT).show()
        }

        binding.btnShowCalendar.setOnClickListener {
            val datePicker = DatePicker()
            datePicker.show(supportFragmentManager, "datePicker")
        }

        binding.btnShowAlertDialog.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Keluar")
            builder.setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
            builder.setPositiveButton("Ya") { dialog, which ->
                finish()
            }
            builder.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        binding.btnShowCustomDialog.setOnClickListener {
            val dialog = DialogExit()
            dialog.show(supportFragmentManager, "dialogExit")
        }


    }

    override fun onDateSet(p0: android.widget.DatePicker?, p1: Int, p2: Int, p3: Int) {
        // Gunakan p1, p2, p3 untuk mendapatkan tahun, bulan, dan tanggal
        val selectedDate = "$p3/${p2 + 1}/$p1"  // Format: DD/MM/YYYY
        Toast.makeText(this@MainActivity, selectedDate, Toast.LENGTH_SHORT).show()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        // Gunakan p1 dan p2 untuk mendapatkan jam dan menit
        val selectedTime = String.format("%02d:%02d", p1, p2)
        Toast.makeText(
            this@MainActivity, selectedTime,
            Toast.LENGTH_SHORT
        ).show()
    }


}
