package com.example.todo.ui.Fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.todo.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    lateinit var calendar: Calendar
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(requireContext(), R.style.datepicker, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minutes: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minutes)
        val selectedTime = SimpleDateFormat("HH : mm ", Locale.ENGLISH).format(calendar.time)

        val bundle = Bundle()
        bundle.putString("SELECTED_TIME", selectedTime)
        bundle.putInt("HOUR", hourOfDay)
        bundle.putInt("MINUTE", minutes)

        setFragmentResult("REQUEST_TIME_KEY", bundle)


    }
}