package com.example.calculator

import android.os.Bundle
import android.util.Log // Import the Log class for logging
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Clear all inputs and outputs when the clear button is clicked
        binding.alCl.setOnClickListener {
            binding.input.text = ""
            binding.output.text = ""
        }

        // Clear the last character in the input field
        binding.clear.setOnClickListener {
            if (binding.input.text.isNotEmpty()) {
                binding.input.text = binding.input.text.toString().dropLast(1)
                updateOutput() // Update the output dynamically as input changes
            }
        }

        // Number and operator button listeners
        binding.zero.setOnClickListener { onDigitClicked("0") }
        binding.one.setOnClickListener { onDigitClicked("1") }
        binding.two.setOnClickListener { onDigitClicked("2") }
        binding.three.setOnClickListener { onDigitClicked("3") }
        binding.four.setOnClickListener { onDigitClicked("4") }
        binding.five.setOnClickListener { onDigitClicked("5") }
        binding.six.setOnClickListener { onDigitClicked("6") }
        binding.seven.setOnClickListener { onDigitClicked("7") }
        binding.eight.setOnClickListener { onDigitClicked("8") }
        binding.nine.setOnClickListener { onDigitClicked("9") }
        binding.dot.setOnClickListener { onDigitClicked(".") }
        binding.plus.setOnClickListener { onDigitClicked("+") }
        binding.mainus.setOnClickListener { onDigitClicked("-") }
        binding.x.setOnClickListener { onDigitClicked("*") }
        binding.devision.setOnClickListener { onDigitClicked("/") }
        binding.startBack.setOnClickListener { onDigitClicked("(") }
        binding.endBack.setOnClickListener { onDigitClicked(")") }

        // Evaluate the expression when the equals button is clicked
        binding.equle.setOnClickListener {
            equle()
        }
    }

    // Function to handle digit and operator button clicks
    private fun onDigitClicked(digitOrOperator: String) {
        binding.input.append(digitOrOperator)
        updateOutput() // Update the output dynamically
    }

    // Function to update the output dynamically as the input changes
    private fun updateOutput() {
        if (binding.input.text.isNotEmpty()) {
            try {
                val expression = ExpressionBuilder(binding.input.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()

                if (result == longResult.toDouble()) {
                    binding.output.text = longResult.toString()
                } else {
                    binding.output.text = result.toString()
                }
            } catch (e: Exception) {
                binding.output.text = "" // Clear output if there's an error
            }
        }
    }

    // Function to evaluate the final result when the equals button is clicked
    private fun equle() {
        try {
            val expression = ExpressionBuilder(binding.input.text.toString()).build()
            val result = expression.evaluate()
            val longResult = result.toLong()

            if (result == longResult.toDouble()) {
                binding.output.text = longResult.toString()
            } else {
                binding.output.text = result.toString()
            }
            // Reset the input after showing the result
            binding.input.text = ""
        } catch (e: Exception) {
            Log.e("Calculation Error", e.toString()) // Corrected log statement
            binding.output.text = "Error"
        }
    }
}
