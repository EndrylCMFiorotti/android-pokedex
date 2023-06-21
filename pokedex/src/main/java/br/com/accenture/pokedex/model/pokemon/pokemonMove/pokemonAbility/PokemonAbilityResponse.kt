package br.com.accenture.pokedex.model.pokemon.pokemonMove.pokemonAbility

import br.com.accenture.pokedex.model.presentation.PokemonAbilityPresentation
import com.google.gson.annotations.SerializedName

data class PokemonAbilityResponse(
    @SerializedName("flavor_text_entries")
    var flavorTextEntries: List<AbilityDescription>,
    @SerializedName("power")
    var power: Int?,
    @SerializedName("type")
    var type: AbilityType,
    @SerializedName("damage_class")
    var damage: AbilityDamage
) {
    fun toAbilityPresentation() : PokemonAbilityPresentation = PokemonAbilityPresentation(
        description = flavorTextEntries[0].flavorText,
        power = power ?: 0,
        type = type.nameType,
        damage = damage.nameDamage
    )
}