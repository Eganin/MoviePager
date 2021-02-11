package com.example.moviesapp.ui.presentation.movies.view.details

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.CalendarView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.moviesapp.R
import com.example.moviesapp.model.entities.movies.details.ResponseMovieDetail
import java.util.*


class CalendarView(private val movie: ResponseMovieDetail?=null) : DialogFragment() {

    private var calendar: CalendarView? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var currentDate : Calendar =  Calendar.getInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.fragment_calendar, null, false)
        calendar = view?.findViewById(R.id.calendar_view)

        calendar?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            run {
                currentDate = Calendar.getInstance()
                currentDate.set(year,month , dayOfMonth)
            }
        }

        val builder = context?.let { AlertDialog.Builder(it) }

        builder?.let {
            it.setTitle(getString(R.string.select_movie))
                .setView(view)
                .setPositiveButton(
                    getString(R.string.yes_dialog)
                ) { dialog, _ ->
                    run {
                        checkPermissionsCalendar()
                        dialog.cancel()
                    }
                }
                .setNegativeButton(getString(R.string.no_dialog)) { dialog, _ -> dialog.cancel() }
        }

        return builder?.create()!!
    }

    private fun checkPermissionsCalendar() {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_CALENDAR)
                        == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CALENDAR)
                        == PackageManager.PERMISSION_GRANTED-> addCalendarEvent()

                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR) &&
                        shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)->
                    showPermissionExplanation()

                else -> requestPermissions()

            }
        }
    }

    private fun showPermissionExplanation() {
        context?.let {
            AlertDialog.Builder(it)
                .setMessage(getString(R.string.access_text))
                .setPositiveButton(getString(R.string.ok_dialog)) { dialog, _ ->
                    requestPermissions()
                    addCalendarEvent()
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.cancel_dialog)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun requestPermissions() {
        context?.let {
            requestPermissionLauncher.launch(Manifest.permission.READ_CALENDAR)
            requestPermissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)
        }
    }

    private fun addCalendarEvent() {
        calendar?.let{
            val insertCalendarIntent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, movie?.originalTitle)
                .putExtra(CalendarContract.Events.DESCRIPTION,movie?.overview)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Home")
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, currentDate.timeInMillis)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, currentDate.timeInMillis + movie?.runtime!!)

            startActivity(insertCalendarIntent)
        }

    }
}