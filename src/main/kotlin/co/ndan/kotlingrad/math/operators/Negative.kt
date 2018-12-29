package co.ndan.kotlingrad.math.operators

import co.ndan.kotlingrad.math.algebra.Field
import co.ndan.kotlingrad.math.functions.Function
import co.ndan.kotlingrad.math.functions.UnaryFunction
import co.ndan.kotlingrad.math.types.Var

class Negative<X: Field<X>>(override val arg: Function<X>): UnaryFunction<X>(arg) {
  override val value: X
    get() = -arg.value

  override fun differentiate(ind: Var<X>) = -arg.differentiate(ind)

  override fun toString() = "-$arg"

  override fun unaryMinus() = arg
}