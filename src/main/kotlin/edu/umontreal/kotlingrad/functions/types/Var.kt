package edu.umontreal.kotlingrad.functions.types

import edu.umontreal.kotlingrad.algebra.Field
import edu.umontreal.kotlingrad.numerical.FieldPrototype
import edu.umontreal.kotlingrad.functions.Function

class Var<X: Field<X>>(
    override val prototype: FieldPrototype<X>,
    val value: X = prototype.zero,
    private var name: String = randomDefaultName()
): Function<X>(emptySet()) {
  override val variables: Set<Var<X>> = setOf(this)

  override fun invoke(map: Map<Var<X>, X>): X = if (map[this] != null) map[this]!! else value

  override fun diff(ind: Var<X>) = if (this === ind) one else Zero(prototype)

  override fun toString() = name

  override fun div(divisor: Function<X>) = if (divisor === this) one else super.div(divisor)

  companion object {
    private fun randomDefaultName() = ('a'..'z').map { it }.shuffled().subList(0, 4).joinToString("")
  }
}