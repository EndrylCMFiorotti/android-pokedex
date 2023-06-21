package br.com.accenture.pokedex.utils

enum class ColorType(val color: String) {
    BUG("#07AB18"),
    DARK("#5B5466"),
    DRAGON("#086EC0"),
    ELECTRIC("#FBD100"),
    FAIRY("#FB89EB"),
    FIGHTING("#E0306A"),
    FIRE("#EA8C42"),
    FLYING("#89AAE3"),
    GHOST("#594593"),
    GRASS("#38BF4B"),
    GROUND("#E89236"),
    ICE("#4CD1C0"),
    NORMAL("#919AA2"),
    POISON("#B567CE"),
    PSYCHIC("#FF6678"),
    ROCK("#C8B686"),
    STEEL("#5A8EA2"),
    WATER("#3692DC"),
    NONE("#F0F0F0");

    companion object {
        fun backgroundColor(type: String): String = when (type) {
            "bug" -> BUG.color
            "dark" -> DARK.color
            "dragon" -> DRAGON.color
            "electric" -> ELECTRIC.color
            "fairy" -> FAIRY.color
            "fighting" -> FIGHTING.color
            "fire" -> FIRE.color
            "flying" -> FLYING.color
            "ghost" -> GHOST.color
            "grass" -> GRASS.color
            "ground" -> GROUND.color
            "ice" -> ICE.color
            "normal" -> NORMAL.color
            "poison" -> POISON.color
            "psychic" -> PSYCHIC.color
            "rock" -> ROCK.color
            "steel" -> STEEL.color
            "water" -> WATER.color
            else -> NONE.color
        }
    }
}