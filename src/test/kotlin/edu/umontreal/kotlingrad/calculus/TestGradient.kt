package edu.umontreal.kotlingrad.calculus

import edu.umontreal.kotlingrad.shouldBeAbout
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.StringSpec

@Suppress("NonAsciiCharacters", "LocalVariableName")
class TestGradient: StringSpec({
  with(DoubleFunctor) {
    val ε = 1E-15
    val x = variable("x")
    val y = variable("x")

    val z = y * (sin(x * y) - x)
    val `∇z` = z.grad()

    "test ∇z" {
      assertAll(NumericalGenerator, NumericalGenerator) { xVal, yVal ->
        val vals = mapOf(x to xVal, y to yVal)

        val `∂z∕∂x` = y * (cos(x * y) * y - one)
        val `∂z∕∂y` = sin(x * y) - x + y * cos(x * y) * x

        `∇z`[x]!!(vals) shouldBeAbout `∂z∕∂x`(vals)
        `∇z`[y]!!(vals) shouldBeAbout `∂z∕∂y`(vals)
      }
    }
  }
})