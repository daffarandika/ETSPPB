package com.itsrobocon.ets_ppb.di

import androidx.room.Room
import com.itsrobocon.ets_ppb.feature_member.data.AppDatabase
import com.itsrobocon.ets_ppb.feature_member.data.MemberRepository
import com.itsrobocon.ets_ppb.feature_member.data.MemberRepositoryImpl
import com.itsrobocon.ets_ppb.feature_member.ui.detail.MemberDetailViewModel
import com.itsrobocon.ets_ppb.feature_member.ui.list.MemberListViewModel
import com.itsrobocon.ets_ppb.feature_member.ui.registration.RegistrationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "library_database",
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }

    single { get<AppDatabase>().memberDao() }

    single<MemberRepository> { MemberRepositoryImpl(get()) }

    viewModelOf(::RegistrationViewModel)
    viewModelOf(::MemberListViewModel)
    viewModelOf(::MemberDetailViewModel)
}
