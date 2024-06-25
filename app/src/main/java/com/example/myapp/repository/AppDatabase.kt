package com.example.myapp.repository


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Diet::class, DietMenu::class , Category::class, Recipe::class, Ingredient::class, RecipeIngredient::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): recipeDao

    companion object {
        private const val DB_NAME = "rp.db"

        fun getDb(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).createFromAsset(DB_NAME).build()
        }
    }
}


