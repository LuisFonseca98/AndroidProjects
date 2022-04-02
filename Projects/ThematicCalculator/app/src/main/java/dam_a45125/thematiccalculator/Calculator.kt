package dam_a45125.thematiccalculator

class Calculator {

    fun sum(num1: Double, num2: Double): Double {
        return num1 + num2
    }

    fun sub(num1: Double, num2: Double): Double {
        return num1 - num2
    }

    fun mult(num1: Double, num2: Double): Double {
        return num1 * num2
    }

    fun div(num1: Double, num2: Double): Double {
        return num1 / num2
    }

    fun cosseno(num1: Double): Double {
        return Math.cos(num1)
    }

    fun seno(num1: Double): Double {
        return Math.sin(num1)
    }

    fun tangente(num1: Double): Double {
        return Math.tan(num1)
    }

    fun module(num1: Double, num2: Double): Double {
        return num1 % num2
    }
}