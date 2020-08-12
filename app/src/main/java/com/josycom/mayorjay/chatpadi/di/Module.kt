package com.josycom.mayorjay.chatpadi.di

import com.josycom.mayorjay.chatpadi.ui.chat.ChatViewModel
import org.koin.dsl.module

/**
 * Created by MayorJay
 * App Module for Dependency Injection
 */
val appModule = module {

    factory { ChatViewModel(get()) }
}