package com.getdefault.smsapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.getdefault.smsapplication.data.db.entity.SMSEntity
import java.util.concurrent.Executors


@Database(entities = [SMSEntity::class], version = 2, exportSchema = false)
abstract class SMSRoomDatabase :RoomDatabase() {

    abstract fun smsDao(): SMSDao

}
