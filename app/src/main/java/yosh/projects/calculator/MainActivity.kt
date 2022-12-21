package yosh.projects.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

private const val STATE_PENDING_OPERATION = "pendingOperation"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_OPERAND1_STORED = "Operand1_Stored"

class MainActivity : AppCompatActivity() {

    private var newNumberTv: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newNumberTv = findViewById(R.id.newNumberEt)


    }

    fun onDigit(view: View){
        newNumberTv?.append((view as Button).text)
        lastNumeric = true

    }

    fun onClear(view: View) {
        newNumberTv?.text = ""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            newNumberTv?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    fun onOperator(view: View){
        newNumberTv?.text?.let{ operand ->
            if (lastNumeric && !isOperatorAdded(operand.toString())){
                newNumberTv?.append((view as Button).text )
            }
        }
    }
    private fun isOperatorAdded(value: String): Boolean{
        return if (value.startsWith("-")){
            false
        } else {
            value.contains("/")
                    ||value.contains("*")
                    ||value.contains("+")
                    ||value.contains("-")
        }
    }
    private fun removeRedundantZero(initialResult: String): String{
        var result = initialResult
        if(result.contains(".0")) result.substring(0, initialResult.length -2)
        return result
    }

    fun onEquals(view: View){
        if(lastNumeric){
            var tvValue = newNumberTv?.text.toString()
            var prefix = ""
            try{
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)

                }
                if (tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    newNumberTv?.text = removeRedundantZero((one.toDouble() + two.toDouble()).toString())
                } else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    newNumberTv?.text = removeRedundantZero((one.toDouble() + two.toDouble()).toString())
                } else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    newNumberTv?.text = removeRedundantZero((one.toDouble() / two.toDouble()).toString())
                } else if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    newNumberTv?.text = removeRedundantZero((one.toDouble() * two.toDouble()).toString())
                }


            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}