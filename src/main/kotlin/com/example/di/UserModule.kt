package com.example.di

import com.example.authentication.JwtService
import com.example.data.dao.NoteDao
import com.example.data.dao.NoteDaoImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::NoteDaoImpl){bind<NoteDao>()}
    singleOf(::JwtService)
}