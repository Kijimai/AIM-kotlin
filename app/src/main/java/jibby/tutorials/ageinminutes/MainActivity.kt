package jibby.tutorials.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // only available in the MainActivity class -- make it nullable on boot so it doesn't crash
    private var tvSelectedDateText: TextView? = null
    private var tvMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDateText = findViewById(R.id.tvSelectedDateText)
        tvMinutes = findViewById(R.id.tvMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }


    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                var selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDateText?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val actualDate = sdf.parse(selectedDate)

                // Null safety procedure -- only executes of the variables are not empty
                actualDate?.let {
                    val selectedDateInMinutes = actualDate.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        tvMinutes?.text = (currentDateInMinutes - selectedDateInMinutes).toString()
                    }
                }
            },
            year,
            month,
            day
        )
        // Sets the max date to yesterday
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }
}