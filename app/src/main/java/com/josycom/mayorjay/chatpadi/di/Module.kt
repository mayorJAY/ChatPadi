package com.josycom.mayorjay.chatpadi.di

import com.josycom.mayorjay.chatpadi.ui.chat.ChatViewModel
import org.koin.dsl.module

val appModule = module {

    factory { ChatViewModel(get()) }
}