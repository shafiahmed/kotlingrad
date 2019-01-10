package edu.umontreal.kotlingrad.calculus

import edu.umontreal.kotlingrad.shouldBeAbout
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.StringSpec

@Suppress("NonAsciiCharacters", "LocalVariableName")
class TestTrigonometricDerivatives: StringSpec({
  with(DoubleFunctor) {
    val x = variable("x")
    val y = variable("y")

    "d(sin(x)) / dx should be cos(x)" {
      assertAll(NumericalGenerator) { xVal ->
        val f = sin(x)
        val df_dx = d(f) / d(x)
        df_dx(x to xVal) shouldBeAbout cos(x)(x to xVal)
      }
    }

    "d(cos(x)) / dx should be -sin(x)" {
      assertAll(NumericalGenerator) { xVal ->
        val f = cos(x)
        val df_dx = d(f) / d(x)
        df_dx(x to xVal) shouldBeAbout -sin(x)(x to xVal)
      }
    }

    val z = y * (sin(x * y) - x)
    val `∂z∕∂x` = d(z) / d(x)
    val `∂z∕∂y` = d(z) / d(y)
    val `∂²z∕∂x²` = d(`∂z∕∂x`) / d(x)
    val `∂²z∕∂x∂y` = d(`∂z∕∂x`) / d(y)

    "test z" {
      assertAll(NumericalGenerator, NumericalGenerator) { kx, ky ->
        val numericalAnswer = ky * (kotlin.math.sin(kx * ky) - kx) + 0.0
        z(x to kx, y to ky) shouldBeAbout numericalAnswer
      }
    }

    "test ∂z/∂x" {
      assertAll(NumericalGenerator, NumericalGenerator) { xVal, yVal ->
        val manualDerivative = y * (cos(x * y) * y - one)
        `∂z∕∂x`(x to xVal, y to yVal) shouldBeAbout manualDerivative(x to xVal, y to yVal)
      }
    }

    "test ∂z/∂y" {
      assertAll(NumericalGenerator, NumericalGenerator) { xVal, yVal ->
        val manualDerivative = (sin(x * y) - x + y * cos(x * y) * x)
        `∂z∕∂y`(x to xVal, y to yVal) shouldBeAbout manualDerivative(x to xVal, y to yVal)
      }
    }

    "test ∂²z/∂x²" {
      assertAll(NumericalGenerator, NumericalGenerator) { xVal, yVal ->
        val manualDerivative = (-y * y * y * sin(x * y))
        `∂²z∕∂x²`(x to xVal, y to yVal) shouldBeAbout manualDerivative(x to xVal, y to yVal)
      }
    }

    "test ∂²z/∂x∂y" {
      assertAll(NumericalGenerator, NumericalGenerator) { xVal, yVal ->
        val manualDerivative = (cos(x * y) * y - one + y * (cos(x * y) - y * x * sin(x * y)))
        `∂²z∕∂x∂y`(x to xVal, y to yVal) shouldBeAbout manualDerivative(x to xVal, y to yVal)
      }
    }
  }
})