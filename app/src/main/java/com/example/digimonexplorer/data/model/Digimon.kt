package com.example.digimonexplorer.data.model

data class Skill(val name: String, val description: String)

data class Digimon(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val level: String,
    val attribute: String,
    val type: String,
    val releaseDate: String,
    val description: String,
    val skills: List<Skill>
)

fun DigimonDetailDto.toDigimon(): Digimon = Digimon(
    id = id,
    name = name,
    imageUrl = images?.firstOrNull()?.href,
    level = levels?.firstOrNull()?.level ?: "Unknown",
    attribute = attributes?.firstOrNull()?.attribute ?: "Unknown",
    type = types?.firstOrNull()?.type ?: "Unknown",
    releaseDate = releaseDate ?: "-",
    description = descriptions
        ?.firstOrNull { it.language == "en_us" }
        ?.description ?: "No description available.",
    skills = skills.orEmpty().mapNotNull { s ->
        s.skill?.let { Skill(it, s.description ?: "-") }
    }
)

fun DigimonListItemDto.toBasicDigimon(): Digimon = Digimon(
    id = id, name = name, imageUrl = image,
    level = "Unknown", attribute = "Unknown", type = "Unknown",
    releaseDate = "-", description = "", skills = emptyList()
)
