package com.getdefault.smsapplication.smslist

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.getdefault.smsapplication.data.db.SMSDao
import com.getdefault.smsapplication.data.db.SMSRoomDatabase
import com.getdefault.smsapplication.smslist.SmsListScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDbModule {
    @SmsListScope
    @Provides
    fun provideDataBase(context: Application): SMSRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SMSRoomDatabase::class.java,
            "sms_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
    @SmsListScope
    @Provides
    fun provideSmsRepoDao(database: SMSRoomDatabase): SMSDao {
        return database.smsDao()
    }


}