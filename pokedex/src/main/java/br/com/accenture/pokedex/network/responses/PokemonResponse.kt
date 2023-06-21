package br.com.accenture.pokedex.network.responses

import br.com.accenture.pokedex.model.pokemon.pokemonImage.PokemonImage
import br.com.accenture.pokedex.model.pokemon.pokemonMove.PokemonMove
import br.com.accenture.pokedex.model.pokemon.pokemonType.PokemonType
import br.com.accenture.pokedex.model.presentation.PokemonPresentation
import br.com.accenture.pokedex.model.presentation.PokemonTypesPresentation
import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("height")
    var height: String,
    @SerializedName("weight")
    var weight: String,
    @SerializedName("types")
    var typeList: ArrayList<PokemonType>,
    @SerializedName("sprites")
    var image: PokemonImage,
    @SerializedName("moves")
    var movesList: ArrayList<PokemonMove>
) {
    fun toPokemonPresentation(): PokemonPresentation = PokemonPresentation(
        id = id,
        name = name,
        height = height,
        weight = weight,
        image = image.imgList.type.urlImg,
        types = PokemonTypesPresentation(
            firstType = typeList.first().type.name,
            secondType = if(typeList.count() > 1) typeList[1].type.name else ""
        ),
        moves = movesList.map {
            it.move.moveName
        }
    )
}