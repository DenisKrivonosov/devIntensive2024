package ru.androidschool.intensiv.data.mapper

interface DtoToViewObjectMapper<DTO, VO> {

    fun toViewObject(dto: DTO): VO

    fun toViewObject(list: Collection<DTO>): List<VO> {
        val result = ArrayList<VO>()
        list.mapTo(result) { toViewObject(it) }
        return result
    }
}
