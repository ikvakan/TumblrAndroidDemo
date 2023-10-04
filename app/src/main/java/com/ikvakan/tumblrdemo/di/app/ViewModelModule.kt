package com.ikvakan.tumblrdemo.di.app

import com.ikvakan.tumblrdemo.presentation.screens.posts.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        PostsViewModel(postRepository = get())
    }

}
