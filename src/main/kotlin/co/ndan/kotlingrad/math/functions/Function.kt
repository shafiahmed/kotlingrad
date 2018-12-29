package co.ndan.kotlingrad.math.functions

import co.ndan.kotlingrad.math.algebra.Field
import co.ndan.kotlingrad.math.calculus.Differentiable
import co.ndan.kotlingrad.math.operators.*
import co.ndan.kotlingrad.math.types.UnivariatePolynomialTerm
import co.ndan.kotlingrad.math.types.Var

abstract class Function<X: Field<X>> protected constructor(): Field<Function<X>>, Differentiable<X, Function<X>>, kotlin.Function<X> {
  abstract val value: X
  open val isConstant = false

  operator fun invoke(map: Map<*, X>): X =
    if(value is Var<*> && value in map.keys) (value as Var<X>).value
    else if(value is Function<*>) (value as Function<X>)(map)
    else value

  abstract override fun toString(): String

  abstract override fun differentiate(ind: Var<X>): Function<X>

  override fun plus(addend: Function<X>): Function<X> = Sum(this, addend)

  override fun minus(subtrahend: Function<X>): Function<X> = this + -subtrahend

  override fun times(multiplicand: Function<X>): Function<X> = Product(this, multiplicand)

  override fun div(divisor: Function<X>): Function<X> = this * divisor.inverse()

  override fun inverse(): Function<X> = Inverse(this)

  override fun unaryMinus(): Function<X> = Negative(this)

  override fun times(multiplicand: Long): Function<X> = UnivariatePolynomialTerm(multiplicand, this, 1)

  override fun pow(exponent: Int): Function<X> = UnivariatePolynomialTerm(1L, this, exponent)
}