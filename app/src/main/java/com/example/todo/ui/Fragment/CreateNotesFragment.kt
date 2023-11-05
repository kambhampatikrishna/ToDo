package com.example.todo.ui.Fragment

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todo.R
import com.example.todo.ViewModel.NotesViewModel
import com.example.todo.databinding.FragmentCreateNotesBinding
import com.example.todo.model.Notes
import com.example.todo.ui.Notification
import com.example.todo.ui.channelId
import com.example.todo.ui.notificationId
import com.example.todo.ui.notificationMessage
import com.example.todo.ui.notificationTitle
import java.util.Calendar


class CreateNotesFragment : Fragment() {

    lateinit var viewModel: NotesViewModel

    lateinit var binding: FragmentCreateNotesBinding
    lateinit var title: String
    lateinit var notesDescription: String
    var date: String? = ""
    var time: String? = ""
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var hour: Int = 0
    var minutes: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment'
        binding = FragmentCreateNotesBinding.inflate(inflater, container, false)


        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        binding.ivBackButton.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_createNotesFragment2_to_homeFragment)
        }

        binding.dateFieldLayout.setOnClickListener {
            showCalendar()
        }

        binding.timeFieldLayout.setOnClickListener {
            showClock()
        }
        binding.addNoteButton.setOnClickListener {
            createNotes(it)
        }
        return binding.root

    }

    private fun createNotes(it: View?) {
        title = binding.etTitle.text.toString()
        notesDescription = binding.etNote.text.toString()
        date = date ?: ""
        time = time ?: ""

        val data = Notes(
            null,
            title = title,
            notes = notesDescription,
            date = date,
            time = time
        )
        viewModel.addNotes(data)

        if (date!!.isNotEmpty() && time!!.isNotEmpty()) {
            createNotificationChannel(title, notesDescription)
            scheduleNotification(title, notesDescription)
        }
        Toast.makeText(requireContext(), "Notes Saved", Toast.LENGTH_LONG).show()

        Navigation.findNavController(it!!)
            .navigate(R.id.action_createNotesFragment2_to_homeFragment)
    }

    private fun scheduleNotification(title: String, description: String) {

        val intent = Intent(requireActivity().applicationContext, Notification::class.java)
        intent.putExtra(notificationTitle, title)
        intent.putExtra(notificationMessage, description)

        val pendingIntent = PendingIntent.getBroadcast(
            requireActivity().applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }

    private fun createNotificationChannel(title: String, description: String) {

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, title, importance)
        channel.description = description
        val notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    private fun getTime(): Long {

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minutes)

        return calendar.timeInMillis
    }

    private fun showCalendar() {
        val datePickerFragment = DatePickerFragment()
        val supportFragmentManager = requireActivity().supportFragmentManager
        supportFragmentManager.setFragmentResultListener(
            "REQUEST_KEY",
            viewLifecycleOwner
        ) { resultKey, bundle ->
            if (resultKey == "REQUEST_KEY") {
                binding.date.text = bundle.getString("SELECTED_DATE")
                date = bundle.getString("SELECTED_DATE").toString()
                month = bundle.getInt("MONTH")
                day = bundle.getInt("DAY")
                year = bundle.getInt("YEAR")
            }
        }
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

    private fun showClock() {
        val timePickerFragment = TimePickerFragment()
        val supportFragmentManager = requireActivity().supportFragmentManager
        supportFragmentManager.setFragmentResultListener(
            "REQUEST_TIME_KEY",
            viewLifecycleOwner
        ) { resultKey, bundle ->
            if (resultKey == "REQUEST_TIME_KEY") {
                binding.time.text = bundle.getString("SELECTED_TIME")
                time = bundle.getString("SELECTED_TIME").toString()
                hour = bundle.getInt("HOUR")
                minutes = bundle.getInt("MINUTE")

            }
        }
        timePickerFragment.show(supportFragmentManager, "timePicker")
    }


}