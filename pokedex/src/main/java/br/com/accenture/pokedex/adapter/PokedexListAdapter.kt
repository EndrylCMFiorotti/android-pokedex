package br.com.accenture.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.accenture.pokedex.R
import br.com.accenture.pokedex.databinding.ItemListBinding
import br.com.accenture.pokedex.model.pokedex.PokedexEntries
import br.com.accenture.pokedex.model.presentation.PokedexPresentation

class PokedexListAdapter(
    private val pokemons: List<PokedexPresentation>,
    private val onClick: (PokedexPresentation) -> Unit
) : RecyclerView.Adapter<PokedexListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = pokemons.size

    inner class ViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { onClick(pokemonData()) }
        }

        fun bind() {
            binding.textViewNamePokemon.text = pokemonData().name
        }

        private fun pokemonData() = pokemons[adapterPosition]
    }
}