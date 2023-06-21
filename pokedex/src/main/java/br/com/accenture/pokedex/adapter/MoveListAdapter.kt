package br.com.accenture.pokedex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.accenture.pokedex.databinding.ItemMoveListBinding

class MoveListAdapter(
    private val moves: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<MoveListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMoveListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = moves.size

    inner class ViewHolder(private val binding: ItemMoveListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(moveData())
            }
        }

        fun bind() {
            binding.textViewMovePokemon.text = moveData()
        }

        private fun moveData() = moves[adapterPosition]
    }
}