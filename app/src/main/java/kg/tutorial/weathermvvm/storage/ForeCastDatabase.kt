package kg.tutorial.weathermvvm.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kg.tutorial.weathermvvm.models.ForeCast

@Database(
    entities = [ForeCast::class],
    version = 2,
    exportSchema = false
)

@TypeConverters(ModelsConverter::class, CollectionsConverter::class)
abstract class ForeCastDatabase : RoomDatabase() {
    abstract fun forecastDao(): ForeCastDao

    companion object {
        const val DB_NAME = "forecastDb"
    }

}