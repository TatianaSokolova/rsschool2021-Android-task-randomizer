package com.rsschool.android2021

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var min: EditText? = null
    private var max: EditText? = null

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val minInput: String = min?.text.toString()
            val maxInput: String = max?.text.toString()

            if (minInput.length > 0 && maxInput.length > 0){
                generateButton?.isEnabled = true
            }

        }

        override fun afterTextChanged(s: Editable) {
            try {
                min?.text.toString().toInt()
            } catch (e : NumberFormatException) {
                min?.error = "please enter a number not more than 2 147 483 647"
                generateButton?.isEnabled = false
            }
            try {
                max?.text.toString().toInt()
            } catch (e : NumberFormatException) {
                max?.error = "please enter a number not more than 2 147 483 647"
                generateButton?.isEnabled = false
            }
        }
    }

    private var mListener: FirstFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        min = view.findViewById<EditText>(R.id.min_value)
        max = view.findViewById<EditText>(R.id.max_value)

        generateButton?.isEnabled = false

        min?.addTextChangedListener(textWatcher)
        max?.addTextChangedListener(textWatcher)




        // TODO: val min = ...
        // TODO: val max = ...

        mListener = activity as FirstFragmentListener

        generateButton?.setOnClickListener {
            mListener?.onClickResultButton(
                min?.text.toString().toInt(),
                max?.text.toString().toInt()
            )
            // TODO: send min and max to the SecondFragment
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}