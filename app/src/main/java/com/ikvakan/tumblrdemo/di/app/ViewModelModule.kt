package com.ikvakan.tumblrdemo.di.app

import android.os.Build
import androidx.annotation.RequiresExtension
import com.ikvakan.tumblrdemo.presentation.screens.posts.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
val viewModelModule = module {

    viewModel {
        PostsViewModel(postRepository = get(), remoteExceptionMapper = get())
    }

}
