package com.zohaibraza.composetest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.zohaibraza.composetest.data.constants.RoomConstants

@Entity(tableName = RoomConstants.TABLE_TRACKS)
data class Track (

    @SerializedName("wrapperType")
    var wrapperType: String? = null,

    @SerializedName("kind")
    var kind: String? = null,

    @SerializedName("collectionId")
    var collectionId: Int? = null,

    @PrimaryKey
    @SerializedName("trackId")
    var trackId: Int? = null,

    @SerializedName("artistName")
    var artistName: String? = null,

    @SerializedName("collectionName")
    var collectionName: String? = null,

    @SerializedName("trackName")
    var trackName: String? = null,

    @SerializedName("collectionCensoredName")
    var collectionCensoredName: String? = null,

    @SerializedName("trackCensoredName")
    var trackCensoredName: String? = null,

    @SerializedName("collectionArtistId")
    var collectionArtistId: Int? = null,

    @SerializedName("collectionArtistViewUrl")
    var collectionArtistViewUrl: String? = null,

    @SerializedName("collectionViewUrl")
    var collectionViewUrl: String? = null,

    @SerializedName("trackViewUrl")
    var trackViewUrl: String? = null,

    @SerializedName("previewUrl")
    var previewUrl: String? = null,

    @SerializedName("artworkUrl30")
    var artworkUrl30: String? = null,

    @SerializedName("artworkUrl60")
    var artworkUrl60: String? = null,

    @SerializedName("artworkUrl100")
    var artworkUrl100: String? = null,

    @SerializedName("collectionPrice")
    var collectionPrice: Double? = null,

    @SerializedName("trackPrice")
    var trackPrice: Double? = null,

    @SerializedName("trackRentalPrice")
    var trackRentalPrice: Double? = null,

    @SerializedName("collectionHdPrice")
    var collectionHdPrice: Double? = null,

    @SerializedName("trackHdPrice")
    var trackHdPrice: Double? = null,

    @SerializedName("trackHdRentalPrice")
    var trackHdRentalPrice: Double? = null,

    @SerializedName("releaseDate")
    var releaseDate: String? = null,

    @SerializedName("collectionExplicitness")
    var collectionExplicitness: String? = null,

    @SerializedName("trackExplicitness")
    var trackExplicitness: String? = null,

    @SerializedName("trackCount")
    var trackCount: Int? = null,

    @SerializedName("trackNumber")
    var trackNumber: Int? = null,

    @SerializedName("trackTimeMillis")
    var trackTimeMillis: Int? = null,

    @SerializedName("country")
    var country: String? = null,

    @SerializedName("currency")
    var currency: String? = null,

    @SerializedName("primaryGenreName")
    var primaryGenreName: String? = null,

    @SerializedName("contentAdvisoryRating")
    var contentAdvisoryRating: String? = null,

    @SerializedName("shortDescription")
    var shortDescription: String? = null,

    @SerializedName("longDescription")
    var longDescription: String? = null,

    @SerializedName("hasITunesExtras")
    var hasITunesExtras: Boolean? = null,

    var isFavourite: Boolean = false
)