package com.iamkdblue.randomjoke.model

data class Joke(
    val id: Int,
    val punchline: String,
    val setup: String,
    val type: String
)