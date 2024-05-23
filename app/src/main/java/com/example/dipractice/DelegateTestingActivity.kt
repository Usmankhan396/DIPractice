package com.example.dipractice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class DelegateTestingActivity : AppCompatActivity(), AnalyticsLogger by AnalyticsLoggerImpl() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        registerLifecycleOwner(this)
//        println(newProperty)
    }

    private fun initView() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_delegate_testing)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    //Example of property Delegates

    private val newProperty by lazy {
        println("Lazy property has been created")
        "Property Delegate"
    }

}


interface AnalyticsLogger {
    fun registerLifecycleOwner(owner: LifecycleOwner)
}

class AnalyticsLoggerImpl : AnalyticsLogger, LifecycleEventObserver {
    private var lifecycleOwner: LifecycleOwner? = null

    override fun registerLifecycleOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> println("On Resume method has just been called")
            Lifecycle.Event.ON_PAUSE -> println("On Pause method has just been called")
            else -> println("Other methods have been called")
        }
    }

}

