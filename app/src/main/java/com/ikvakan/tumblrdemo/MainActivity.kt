package com.ikvakan.tumblrdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ikvakan.tumblrdemo.theme.TumblrDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TumblrDemoTheme {
            }
        }
    }
}
