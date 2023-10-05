package com.ikvakan.tumblrdemo.data.mock

import com.ikvakan.tumblrdemo.domain.model.Post

class MockData {
    val postEntities = List(size = 20) {
        Post(
            id = it.toLong(),
            blogTitle = "Marvel Blog $it",
            summary = "Lorem ipsum dolor sit amet. Eos libero dolores ut voluptatem rerum " +
                "ut atque libero id obcaecati veniam aut facere rerum? " +
                "Id nobis earum rem nobis praesentium qui consequatur galisum eos autem illo. ",
            imageUrl = "https://st2.depositphotos.com/1009634/7235/v/950/" +
                "depositphotos_72350117-stock-illustration-no-user-profile-picture-hand.jpg",
            isFavorite = false
        )
    }
}
