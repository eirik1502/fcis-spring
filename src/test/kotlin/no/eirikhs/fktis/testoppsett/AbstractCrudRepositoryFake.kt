package no.eirikhs.fktis.testoppsett

import org.springframework.data.repository.CrudRepository
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KProperty1

abstract class AbstractCrudRepositoryFake<T : Any, ID : Any>(
    val lagId: (entity: T) -> ID,
    val getEntityId: (entity: T) -> ID?,
    val setEntityId: (entity: T, id: ID) -> T,
    private val uniqueConstraints: List<KProperty1<T, Any?>> = emptyList(),
) : CrudRepository<T, ID> {
    val entities: MutableMap<ID, T> = ConcurrentHashMap()

    override fun <S : T?> save(entity: S & Any): S & Any {
        val entityWithId =
            if (getEntityId(entity) == null) {
                setEntityId(entity, lagId(entity))
            } else {
                entity
            }
        checkUniqueConstraints(entityWithId)
        entities[getEntityId(entityWithId)!!] = entityWithId
        @Suppress("UNCHECKED_CAST")
        return entityWithId as (S & Any)
    }

    override fun <S : T> saveAll(entities: MutableIterable<S>): MutableIterable<S> {
        entities.forEach {
            save(it)
        }
        return entities
    }

    override fun findById(id: ID): Optional<T> = Optional.ofNullable(entities.values.find { getEntityId(it) == id })

    override fun existsById(id: ID): Boolean = entities.values.any { getEntityId(it) == id }

    override fun findAll(): MutableIterable<T> = entities.values

    override fun findAllById(ids: MutableIterable<ID>): MutableIterable<T> =
        entities.values.filter { getEntityId(it) in ids }.toMutableList()

    override fun count(): Long = entities.size.toLong()

    override fun deleteById(id: ID) {
        entities.remove(id)
    }

    override fun delete(entity: T) {
        entities.remove(getEntityId(entity))
    }

    override fun deleteAllById(ids: MutableIterable<ID>) {
        ids.forEach { deleteById(it) }
    }

    override fun deleteAll(entities: MutableIterable<T>) {
        entities.forEach { delete(it) }
    }

    override fun deleteAll() {
        entities.clear()
    }

    private fun checkUniqueConstraints(entity: T) {
        val entityId = getEntityId(entity)
        val otherEntities = this.entities.filterKeys { id -> id != entityId }.values
        uniqueConstraints.forEach { constraintProp ->
            val value = constraintProp(entity)
            if (otherEntities.any { constraintProp(it) == value }) {
                throw IllegalStateException(
                    "Insertion of entity violates unique constraint on ${constraintProp.name}, with value: $value, entity: $entity",
                )
            }
        }
    }
}
