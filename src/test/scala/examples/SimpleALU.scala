package examples

import Chisel._
import Chisel.iotesters._

class SimpleALUTests(c: SimpleALU, b: Option[Backend] = None) extends PeekPokeTester(c, _backend=b) {
  for (n <- 0 until 64) {
    val a      = rnd.nextInt(16)
    val b      = rnd.nextInt(16)
    val opcode = rnd.nextInt(4)
  // for (a <- 0 until 16) {
    // for (b <- 0 until 16) {
      // for (opcode <- 0 until 4) {
        var output = 0
        if (opcode == 0) {
          output = ((a+b) & 0xF)
        } else if (opcode == 1) {
          output = ((a-b) & 0xF)
        } else if (opcode == 2) {
          output = a
        } else {
          output = b
        }
        poke(c.io.a, a)
        poke(c.io.b, b)
        poke(c.io.opcode, opcode)
        step(1)
        expect(c.io.out, output)
  }
      // }}}
}