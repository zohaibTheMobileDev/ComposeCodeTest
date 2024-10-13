package com.zohaibraza.composetest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zohaibraza.composetest.model.Track

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<Track>)

    @Query("SELECT * FROM tracks WHERE trackName LIKE '%' || :query || '%'")
    suspend fun searchTracks(query: String): List<Track>

    @Query("SELECT * FROM tracks")
    suspend fun getAllTracks(): List<Track>

    @Query("DELETE FROM tracks")
    suspend fun deleteAllTracks()

    @Query("UPDATE tracks SET isFavourite = :isFavourite WHERE trackId = :trackId")
    suspend fun updateFavouriteStatus(trackId: Int, isFavourite: Boolean)

}

