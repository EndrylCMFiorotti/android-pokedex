package br.com.accenture.pokedex.view.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import br.com.accenture.pokedex.adapter.MoveListAdapter
import br.com.accenture.pokedex.databinding.CustomDialogBinding
import br.com.accenture.pokedex.databinding.DataPokemonBinding
import br.com.accenture.pokedex.databinding.ErrorDialogBinding
import br.com.accenture.pokedex.databinding.FragmentPokemonBinding
import br.com.accenture.pokedex.model.presentation.PokemonAbilityPresentation
import br.com.accenture.pokedex.model.presentation.PokemonPresentation
import br.com.accenture.pokedex.utils.ColorType
import br.com.accenture.pokedex.viewModel.PokemonViewModel
import coil.load
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class PokemonFragment : Fragment() {
    private lateinit var binding: FragmentPokemonBinding
    private lateinit var dialogBinding: CustomDialogBinding
    private lateinit var errorDialogBinding: ErrorDialogBinding
    private lateinit var dialog: Dialog
    private val viewModel: PokemonViewModel by viewModel()
    private val args: PokemonFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        sendRequest()
    }

    private fun sendRequest() {
        viewModel.getPokemon(args.name.ifEmpty { args.id }.toString())
        startLoading()
    }

    private fun startLoading() {
        with(binding) {
            dataView.isVisible = false
            shimmerView.startShimmer()
        }
    }

    private fun stopLoading() {
        with(binding) {
            dataView.isVisible = true
            shimmerView.isVisible = false
            shimmerView.stopShimmer()
        }
    }

    private fun setUpObservers() {
        with(viewModel) {
            pokemon.observe(viewLifecycleOwner) {
                displayPokemon(it)
            }
            notFound.observe(viewLifecycleOwner) {
                configDialogError()
            }
            pokemonDescription.observe(viewLifecycleOwner) {
                displayPokemonDescription(it)
                stopLoading()
            }
            pokemonDescriptionEmpty.observe(viewLifecycleOwner) {
                binding.includeData.textViewDescription.isVisible = false
                stopLoading()
            }
            setSecondTypeInvisible.observe(viewLifecycleOwner) {
                setVisibilitySecondType()
            }
            ability.observe(viewLifecycleOwner) {
                visibilityLoading(View.VISIBLE, View.INVISIBLE)
                displayDialogContent(it)
            }
        }
    }

    private fun configDialogError() {
        errorDialogBinding = ErrorDialogBinding.inflate(LayoutInflater.from(requireContext()))
        dialog = Dialog(requireContext()).apply {
            setContentView(errorDialogBinding.root)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }
        Executors.newSingleThreadScheduledExecutor().schedule({
            dialog.dismiss()
            redirect()
        }, 2, TimeUnit.SECONDS)
    }

    private fun redirect() {
        val action = PokemonFragmentDirections.fromPokemonFragmentToPokedexFragment()
        findNavController().navigate(action)
    }

    private fun displayPokemon(pokemon: PokemonPresentation) {
        with(binding.includeData) {
            with(pokemon) {
                imageViewPokemon.load(image)
                textViewNamePokemon.text = name
                textViewHeightPokemon.text = HtmlCompat.fromHtml(
                    "<b>Height</b> ${height.toDouble() / 10} m",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                textViewWeightPokemon.text = HtmlCompat.fromHtml(
                    "<b>Weight</b> ${weight.toDouble() / 10} kg",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                textViewType1.text = types.firstType
                textViewType2.text = types.secondType
                setBackgroundColorTypes(this)
                recyclerViewMoves.apply {
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    adapter = MoveListAdapter(moves) {
                        viewModel.getAbility(it)
                        configDialog(it)
                    }
                }
            }
        }
    }

    private fun configDialog(it: String) {
        dialogBinding = CustomDialogBinding.inflate(LayoutInflater.from(requireContext()))
        dialog = Dialog(requireContext()).apply {
            setContentView(dialogBinding.root)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }
        dialogBinding.tvNameAbility.text = it
        visibilityLoading(View.INVISIBLE, View.VISIBLE)
    }

    private fun displayDialogContent(it: PokemonAbilityPresentation) {
        dialogBinding.tvDescription.text = it.description
        dialogBinding.tvType.text = it.type
        dialogBinding.tvPower.text = it.power.toString()
        dialogBinding.tvDamage.text = it.damage
        dialogBinding.btClose.setOnClickListener { dialog.dismiss() }
    }

    private fun visibilityLoading(data: Int, loading: Int) {
        dialogBinding.viewContent.visibility = data
        dialogBinding.prLoading.visibility = loading
    }

    private fun DataPokemonBinding.setBackgroundColorTypes(pokemon: PokemonPresentation) {
        with(pokemon.types) {
            textViewType1.background = configDrawable(firstType)
            textViewType2.background = configDrawable(secondType)
        }
    }

    private fun configDrawable(type: String): GradientDrawable = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = BORDER_RADIUS
        setColor(Color.parseColor(ColorType.backgroundColor(type)))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setPadding(LEFT_PADDING, TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING)
        }
    }

    private fun displayPokemonDescription(pokemonDescription: String) {
        binding.includeData.textViewDescription.text = pokemonDescription.replace("\n", " ")
    }

    private fun setVisibilitySecondType() {
        binding.includeData.textViewType2.isVisible = false
    }

    companion object {
        private const val LEFT_PADDING = 32
        private const val RIGHT_PADDING = 32
        private const val TOP_PADDING = 16
        private const val BOTTOM_PADDING = 16
        private const val BORDER_RADIUS = 64F
    }
}