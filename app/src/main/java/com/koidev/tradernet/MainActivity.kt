package com.koidev.tradernet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.koidev.tradernet.di.DaggerAppComponent
import com.koidev.tradernet.ui.theme.TraderNetAppAndroidTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDagger()

        setContent {
            TraderNetAppAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }

    private fun initDagger() {
        (application as TraderNetApp).appComponent.factory.create(MainViewModel::class.java)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TraderNetAppAndroidTheme {
        Greeting("Android")
    }
}