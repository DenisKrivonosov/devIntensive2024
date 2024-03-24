package ru.androidschool.intensiv.data.mapper

interface DtoToEntityMapper<DTO, ENTITY> {

    fun toEntityObject(dto: DTO): ENTITY

    fun toEntityObject(list: Collection<DTO>): List<ENTITY> {
        val result = ArrayList<ENTITY>()
        list.mapTo(result) { toEntityObject(it) }
        return result
    }
}
