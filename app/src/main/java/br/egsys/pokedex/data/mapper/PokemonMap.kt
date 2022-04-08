package br.egsys.pokedex.data.mapper

import br.egsys.pokedex.data.dto.PokemonDto
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.data.util.DomainMapper
import javax.inject.Inject

class PokemonMap @Inject constructor() : DomainMapper<PokemonDto, Pokemon> {

    override fun mapFromDomainModel(domainModel: Pokemon): PokemonDto {

        val listTypes = mutableListOf<String>()

        domainModel.types.forEach {
            listTypes.add(it.type.name)
        }

        return PokemonDto(
            id = domainModel.id.toString(),
            name = domainModel.name,
            types = listTypes.toList(),
            weight = domainModel.weight.toInt(),
            height = domainModel.height.toInt(),
            image = domainModel.sprites.frontDefault
        )
    }

    override fun toEntityList(initial: List<Pokemon>): List<PokemonDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}
