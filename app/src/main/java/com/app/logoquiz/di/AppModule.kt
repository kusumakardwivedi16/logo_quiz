package com.app.logoquiz.di

import android.content.Context
import androidx.room.Room
import com.app.logoquiz.data.db.PreloadCallback
import com.app.logoquiz.data.db.QuestionDao
import com.app.logoquiz.data.db.QuestionDatabase
import com.app.logoquiz.repo.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRepository(dao: QuestionDao): Repository {
        return Repository(dao)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        daoProvider: Provider<QuestionDao>
    ): QuestionDatabase {
        return Room.databaseBuilder(
            context,
            QuestionDatabase::class.java,
            "questions_db"
        ).addCallback(PreloadCallback(context, daoProvider))
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(database: QuestionDatabase): QuestionDao {
        return database.dao
    }

}