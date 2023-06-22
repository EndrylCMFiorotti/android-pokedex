package br.com.accenture.pokedex.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.accenture.pokedex.R
import br.com.accenture.pokedex.adapter.PokedexListAdapter
import br.com.accenture.pokedex.databinding.FragmentPokedexBinding
import br.com.accenture.pokedex.model.presentation.PokedexPresentation
import br.com.accenture.pokedex.viewModel.PokedexViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokedexFragment : Fragment() {
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
        sendRequest()
        onClickSearch()
        setUpToolbar()
    }

    private fun setUpToolbar() {
        binding.tbPokedex.toolbar.title = "pokedex"
    }

    private fun sendRequest() {
        viewModel.getPokemonListToPokedex()
        startLoading()
    }

    private fun setUpObservers() {
        with(viewModel) {
            pokedexList.observe(viewLifecycleOwner) { list ->
                configPokemonList(list)
                configAutoComplete(list)
                stopLoading()
            }
            pokedexListEmpty.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "List is empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configAutoComplete(listPokemonNames: List<PokedexPresentation>) {
        binding.acPokemon.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                listPokemonNames.map { it.name }
            )
        )
    }

    private fun startLoading() {
        binding.RecyclerView.visibility = View.INVISIBLE
        binding.shimmerRv.visibility = View.VISIBLE
        binding.shimmerRv.startShimmer()
    }

    private fun stopLoading() {
        binding.RecyclerView.visibility = View.VISIBLE
        binding.shimmerRv.visibility = View.INVISIBLE
        binding.shimmerRv.stopShimmer()
    }

    private fun configPokemonList(list: List<PokedexPresentation>) {
        binding.RecyclerView.adapter = PokedexListAdapter(list) {
            onClickPokedex(it)
        }
    }

    private fun onClickSearch() {
        binding.btSend.setOnClickListener {
            val action = PokedexFragmentDirections.fromPokedexFragmentToPokemonFragment(
                id = 0,
                name = binding.acPokemon.text.toString()
            )
            findNavController().navigate(action)
        }
    }

    private fun onClickPokedex(pokedexPresentation: PokedexPresentation) {
        val action = PokedexFragmentDirections.fromPokedexFragmentToPokemonFragment(
            id = pokedexPresentation.id,
            name = ""
        )
        findNavController().navigate(action)
    }
}