package com.ikvakan.tumblrdemo.domain

import com.ikvakan.tumblrdemo.data.remote.model.ResponseRemoteDto
import com.ikvakan.tumblrdemo.domain.model.PostEntity

interface Mapper<DtoModel, DomainEntity> {
    fun toEntityList(dtoModel: DtoModel): List<DomainEntity>
}

class PostEntityMapper : Mapper<ResponseRemoteDto, PostEntity> {
    override fun toEntityList(dtoModel: ResponseRemoteDto): List<PostEntity> {
        return dtoModel.posts.map { e ->
            PostEntity(
                id = e.id,
                blogTitle = dtoModel.blog?.title ?: "",
                summary = e.summary,
                imageUrl = e.photos.firstOrNull()?.image?.url ?: ""
            )
        }.toList()
    }
}
