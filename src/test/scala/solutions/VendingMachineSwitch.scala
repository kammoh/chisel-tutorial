// See LICENSE.txt for license details.
package solutions

import Chisel.iotesters.PeekPokeTester
import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, TesterOptions, TesterOptionsManager}
import firrtl_interpreter.InterpreterOptions

class VendingMachineSwitchTests(c: VendingMachineSwitch) extends PeekPokeTester(c) {
  var money = 0
  var isValid = false

  val coins: Stream[Int] = List(5, 5, 10, 0).toStream #::: coins
  val coinsIter = coins.iterator

  for (t <- 0 until 20) {
    val coin     = rnd.nextInt(3)*5
    val isNickel = coin == 5
    val isDime   = coin == 10

    // Advance circuit
    poke(c.io.nickel, if (isNickel) 1 else 0)
    poke(c.io.dime,   if (isDime) 1 else 0)
    step(1)

    // Advance model
    money = if (isValid) 0 else money + coin
    isValid = money >= 20

    // Compare
    expect(c.io.valid, if (isValid) 1 else 0)
  }
}

class VendingMachineSwitchTester extends ChiselFlatSpec {
  it should "run correctly" in {
    val manager = new TesterOptionsManager {
      testerOptions = testerOptions.copy(backendName = "firrtl", testerSeed = 7L)
      interpreterOptions = interpreterOptions.copy(writeVCD = true)
    }
    iotesters.Driver.execute(() => new VendingMachineSwitch, manager)(c => new VendingMachineSwitchTests(c)) should be (true)
  }
}

