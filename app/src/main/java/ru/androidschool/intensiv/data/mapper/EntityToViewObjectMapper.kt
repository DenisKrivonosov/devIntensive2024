package ru.androidschool.intensiv.data.mapper

interface EntityToViewObjectMapper<ENTITY, VO> {

    fun toViewObject(entity: ENTITY): VO

    fun toViewObject(list: Collection<ENTITY>): List<VO> {
        val result = ArrayList<VO>()
        list.mapTo(result) { toViewObject(it) }
        return result
    }
}
