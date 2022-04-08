package br.egsys.pokedex.data.util

interface DomainMapper<T, DomainModel> {
    fun mapFromDomainModel(domainModel: DomainModel): T
    fun toEntityList(initial: List<DomainModel>): List<T>
}
