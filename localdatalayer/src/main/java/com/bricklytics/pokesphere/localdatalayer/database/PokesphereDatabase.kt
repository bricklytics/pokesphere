package com.bricklytics.pokesphere.localdatalayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bricklytics.pokesphere.localdatalayer.feature.pokemon.PokemonDao
import com.bricklytics.pokesphere.localdatalayer.feature.pokemon.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1
)
abstract class PokesphereDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: PokesphereDatabase? = null

        fun getDatabase(context: Context): PokesphereDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokesphereDatabase::class.java,
                    "PokesphereDatabase.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}