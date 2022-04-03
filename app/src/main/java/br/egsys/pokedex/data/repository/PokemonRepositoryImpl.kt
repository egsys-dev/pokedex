package br.egsys.pokedex.data.repository

import br.egsys.pokedex.data.service.Service
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val service: Service
) : PokemonRepository
