package com.cuecall.app.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.cuecall.app.BuildConfig
import com.cuecall.app.data.local.AppDatabase
import com.cuecall.app.data.local.dao.*
import com.cuecall.app.data.remote.source.FirestoreTokenSource
import com.cuecall.app.data.repository.*
import com.cuecall.app.domain.repository.*
import com.cuecall.app.printer.EscPosPrinterManager
import com.cuecall.app.printer.MockPrinterManager
import com.cuecall.app.printer.PrinterManager
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cuecall_settings")

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideClinicDao(db: AppDatabase): ClinicDao = db.clinicDao()
    @Provides fun provideServiceDao(db: AppDatabase): ServiceDao = db.serviceDao()
    @Provides fun provideCounterDao(db: AppDatabase): CounterDao = db.counterDao()
    @Provides fun provideQueueDayDao(db: AppDatabase): QueueDayDao = db.queueDayDao()
    @Provides fun provideTokenDao(db: AppDatabase): TokenDao = db.tokenDao()
    @Provides fun provideCallEventDao(db: AppDatabase): CallEventDao = db.callEventDao()
    @Provides fun provideDeviceDao(db: AppDatabase): DeviceDao = db.deviceDao()

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore
}

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindClinicRepository(impl: ClinicRepositoryImpl): ClinicRepository

    @Binds @Singleton
    abstract fun bindServiceRepository(impl: ServiceRepositoryImpl): ServiceRepository

    @Binds @Singleton
    abstract fun bindCounterRepository(impl: CounterRepositoryImpl): CounterRepository

    @Binds @Singleton
    abstract fun bindQueueDayRepository(impl: QueueDayRepositoryImpl): QueueDayRepository

    @Binds @Singleton
    abstract fun bindTokenRepository(impl: TokenRepositoryImpl): TokenRepository

    @Binds @Singleton
    abstract fun bindCallEventRepository(impl: CallEventRepositoryImpl): CallEventRepository

    @Binds @Singleton
    abstract fun bindTokenSequenceRepository(impl: TokenSequenceRepositoryImpl): TokenSequenceRepository

    @Binds @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object PrinterModule {

    @Provides
    @Singleton
    fun provideBluetoothAdapter(@ApplicationContext context: Context): BluetoothAdapter? =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager)?.adapter

    @Provides
    @Singleton
    fun providePrinterManager(
        mockPrinterManager: MockPrinterManager,
        escPosPrinterManager: EscPosPrinterManager
    ): PrinterManager {
        // Use mock printer in debug builds so tests work without hardware
        return if (BuildConfig.DEBUG) mockPrinterManager else escPosPrinterManager
    }
}
