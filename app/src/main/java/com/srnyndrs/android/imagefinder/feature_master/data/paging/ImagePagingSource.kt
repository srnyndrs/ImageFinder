package com.srnyndrs.android.imagefinder.feature_master.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.srnyndrs.android.imagefinder.core.data.remote.ApiService
import com.srnyndrs.android.imagefinder.feature_master.data.mapper.toImageResult
import com.srnyndrs.android.imagefinder.feature_master.domain.model.ImageResult
import retrofit2.HttpException
import java.io.IOException

class ImagePagingSource(
    private val apiService: ApiService,
    private val query: String
): PagingSource<Int, ImageResult>() {

    override fun getRefreshKey(state: PagingState<Int, ImageResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResult> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = apiService.searchImages(text = query, page = nextPageNumber)
            return if(response.stat == "ok") {
                 LoadResult.Page(
                    data = response.photos!!.photo.map { it.toImageResult() },
                    prevKey = null,
                    nextKey = if(response.photos.pages > nextPageNumber) nextPageNumber.plus(1) else null
                )
            } else {
                LoadResult.Error(Throwable(message = response.message!!))
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}