package com.example.modulo2.data.clock.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.modulo2.data.user.data.model.UserEntity
import com.example.modulo2.util.Constants

@Entity(tableName = Constants.DATABASE_CLOCK_TABLE,
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["user_id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE // Optional: Define the action on deletion
    )])
data class ClockEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "clock_id")
    val id: Long = 0,

    @ColumnInfo(name = "clock_title")
    var title: String,

    @ColumnInfo(name = "clock_brand")
    var brand: String,

    @ColumnInfo(name = "clock_price")
    var price: String,

    @ColumnInfo(name = "clock_material")
    var material: String,

    @ColumnInfo(name = "userId")
    var userId: Long
)
