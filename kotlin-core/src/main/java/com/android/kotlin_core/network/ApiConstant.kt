package com.android.kotlin_core.network

object ApiConstant {
    const val BASE_URL: String = "https://api.themoviedb.org/3/"
    const val API_KEY_V3: String = "d063d5beabbcd83c4b1d414a4cce47c4"
    const val API_KEY_V4: String = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMDYzZDViZWFiYmNkODNjNGIxZDQxNGE0Y2NlNDdjNCIsInN1YiI6IjU4ZTU5YTNmYzNhMzY4NzMwMjAzMTVlZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GOkgD7k6NWWdFHAfBTRODpi8VOH0xFhhVG3zF3_p4ns"

    //Api end point
    const val GET_AUTH_GUEST_SESSION_NEW: String = "authentication/guest_session/new"
    const val GET_MOVIE_POPULAR: String = "movie/popular"
}