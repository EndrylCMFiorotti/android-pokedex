package br.com.accenture.pokedex.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import br.com.accenture.pokedex.R
import br.com.accenture.pokedex.adapter.PokedexListAdapter
import br.com.accenture.pokedex.databinding.FragmentPokedexBinding
import br.com.accenture.pokedex.model.pokedex.PokedexEntries
import br.com.accenture.pokedex.model.presentation.PokedexPresentation
import br.com.accenture.pokedex.viewModel.PokedexViewModel

class PokedexFragment : Fragment(R.layout.fragment_pokedex) {
    private lateinit var binding: FragmentPokedexBinding
    private val viewModel: PokedexViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        viewModel.getPokemonListToPokedex()
    }

    private fun setUpObservers() {
        with(viewModel) {
            pokedexList.observe(viewLifecycleOwner) { list ->
                configPokemonList(list)
            }
            pokedexListEmpty.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "List is empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configPokemonList(list: List<PokedexPresentation>) {
        binding.RecyclerView.adapter = PokedexListAdapter(list) {
            onClickPokedex(it)
        }
    }

    private fun onClickPokedex(pokedexPresentation: PokedexPresentation) {
        val action = PokedexFragmentDirections.fromPokedexFragmentToPokemonFragment(
            id = pokedexPresentation.id
        )
        findNavController().navigate(action)
    }
}