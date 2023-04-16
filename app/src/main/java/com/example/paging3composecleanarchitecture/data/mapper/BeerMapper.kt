package com.example.paging3composecleanarchitecture.data.mapper

import com.example.paging3composecleanarchitecture.data.local.entity.BeerEntity
import com.example.paging3composecleanarchitecture.data.remote.dto.BeerDto
import com.example.paging3composecleanarchitecture.domain.model.Beer

fun BeerDto.toBeerEntity(): BeerEntity = BeerEntity(
    id = id,
    name = name,
    tagline = tagline,
    description = description,
    firstBrewed = first_brewed,
    imageUrl = image_url
)

fun BeerEntity.toBeer(): Beer = Beer(
    id = id,
    name = name,
    tagline = tagline,
    description = description,
    firstBrewed = firstBrewed,
    imageUrl = imageUrl
)