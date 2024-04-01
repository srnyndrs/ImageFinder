package com.srnyndrs.android.imagefinder.core.data.remote.dto

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class PhotoResponseDto (
    val photo: Photo,
    val stat: String? = null
)

data class Photo (
    val id: String? = null,
    val secret: String? = null,
    val server: String? = null,
    val farm: Long? = null,
    @SerializedName("dateuploaded")
    val dateUploaded: String? = null,
    @SerializedName("isfavorite")
    val isFavorite: Int? = null,
    val license: String? = null,

    @SerializedName("safety_level")
    val safetyLevel: String? = null,

    val rotation: Long? = null,
    @SerializedName("originalsecret")
    val originalSecret: String? = null,
    @SerializedName("originalformat")
    val originalFormat: String? = null,
    val owner: Owner? = null,
    val title: Content? = null,
    val description: Content? = null,
    val visibility: Visibility? = null,
    val dates: Dates? = null,
    val views: String? = null,
    @SerializedName("editability")
    val editAbility: EditAbility? = null,
    @SerializedName("publiceditability")
    val publicEditAbility: EditAbility? = null,
    val usage: Usage? = null,
    val comments: Content? = null,
    val notes: Notes? = null,
    val people: People? = null,
    val tags: Tags? = null,
    val urls: Urls? = null,
    val media: String? = null
)

data class Content (
    @SerializedName("_content")
    val content: String? = null
)

data class Dates (
    val posted: String? = null,
    val taken: String? = null,
    @SerializedName("takengranularity")
    val takenGranularity: Long? = null,
    @SerializedName("takenunknown")
    val takenUnknown: String? = null,
    @SerializedName("lastupdate")
    val lastUpdate: String? = null
)

data class EditAbility (
    @SerializedName("cancomment")
    val canComment: Int? = null,
    @SerializedName("canaddmeta")
    val canAddMeta: Int? = null
)

data class Notes (
    val note: JsonArray? = null
)

data class Owner (
    @SerializedName("nsid")
    val nsId: String? = null,
    val username: String? = null,
    @SerializedName("realname")
    val realName: String? = null,
    val location: String? = null,
    @SerializedName("iconserver")
    val iconServer: String? = null,
    @SerializedName("iconfarm")
    val iconFarm: Long? = null,

    @SerializedName("path_alias")
    val pathAlias: JsonElement? = null,

    val gift: Gift? = null
)

data class Gift (
    @SerializedName("gift_eligible")
    val giftEligible: Boolean? = null,

    @SerializedName("eligible_durations")
    val eligibleDurations: List<String>? = null,

    @SerializedName("new_flow")
    val newFlow: Boolean? = null
)

data class People (
    @SerializedName("haspeople")
    val hasPeople: Int? = null
)

data class Tags (
    val tag: JsonArray? = null
)

data class Urls (
    val url: List<URL>? = null
)

data class URL (
    val type: String? = null,

    @SerializedName("_content")
    val content: String? = null
)

data class Usage (
    @SerializedName("candownload")
    val canDownload: Int? = null,
    @SerializedName("canblog")
    val canBlog: Int? = null,
    @SerializedName("canprint")
    val canPrint: Int? = null,
    @SerializedName("canshare")
    val canShare: Int? = null
)

data class Visibility (
    @SerializedName("ispublic")
    val isPublic: Int? = null,
    @SerializedName("isfriend")
    val isFriend: Int? = null,
    @SerializedName("isfamily")
    val isFamily: Int? = null
)