package io.github.karino2.suspendtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Test {
    suspend fun susA(a: Int) : Int{
        delay(100L)
        return a+5
    }

    fun normal(a: Int) = Math.sin(a.toDouble()).toInt()

    suspend fun susB(a: Int): Int {
        val b = normal(a)
        val c = susA(b)
        return Math.sin(c.toDouble()).toInt()
    }

    suspend fun susAll(a: Int): Int {
        val b = normal(a)
        val c = b+3
        val d = susA(c)
        val e = normal(d)
        val f = susB(e)
        return normal(f)
    }
}


class MainActivity : AppCompatActivity() {


    fun showMessage(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val test = Test()
        var result = 0
        GlobalScope.launch(Dispatchers.Main) {
            result = test.susAll(3)
            showMessage("result is $result")
        }
    }
}
