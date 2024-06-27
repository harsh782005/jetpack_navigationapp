package com.harsh.jetpack_navigationapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.harsh.jetpack_navigationapp.databinding.FragmentJetBinding
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [jetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class jetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentJetBinding? = null
    var mainActivity: MainActivity? = null
    private val TAG = "LifecycleFragment"
    var simpleDateFormat = SimpleDateFormat("dd/MMM/yyyy")
    var timeFormat = SimpleDateFormat("hh:mm aa")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJetBinding.inflate(layoutInflater)
        return binding?.root
        //return inflater.inflate(R.layout.fragment_jet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btn1?.setOnClickListener {
            if (binding?.etEmail?.text?.toString().isNullOrEmpty()) {
                binding?.etEmail?.error = "enter your mail"

            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding?.etEmail?.text.toString())
                    .matches()
            ) {
                binding?.etEmail?.error = "enter valid email"
            } else {
                var bundle = Bundle()
                bundle.putString("email", binding?.etEmail?.text?.toString())
                findNavController().navigate(R.id.secondFragment, bundle)

            }
        }
        binding?.date?.setOnClickListener {
            var datePickerDialog = DatePickerDialog(
                requireContext(), R.style.datePickerDialogStyle,
                { _, year, month, date ->
                    Log.e(TAG, "year $year month $month date $date")
                    var calendar = Calendar.getInstance()
                    calendar.set(year, month, date)
                    var formattedDate = simpleDateFormat.format(calendar.time)
                    binding?.date?.setText(formattedDate)
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE),
            )
            var calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -10)
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            calendar.add(Calendar.DATE, 20)
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis
            datePickerDialog.show()
        }
        binding?.time?.setOnClickListener {
            TimePickerDialog(
                requireContext(), R.style.timePickerDialogStyle, { _, hour, minute ->
                    Log.e(TAG, "hour $hour minute $minute")
                    var calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    var newCalendar = Calendar.getInstance()
                    newCalendar.set(Calendar.HOUR_OF_DAY, 9)
                    var newCalendar1 = Calendar.getInstance()
                    newCalendar1.set(Calendar.HOUR_OF_DAY, 6)
                    if (calendar.before(newCalendar))
                        Toast.makeText(
                            requireContext(),
                            "this time cannot set",
                            Toast.LENGTH_LONG
                        ).show()
                    if (calendar.after(newCalendar1))
                        Toast.makeText(
                            requireContext(),
                            "this time cannot set",
                            Toast.LENGTH_LONG
                        ).show()

                    binding?.time?.setText(timeFormat.format(calendar.time))
                    

                },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                false
            ).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment jetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            jetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}