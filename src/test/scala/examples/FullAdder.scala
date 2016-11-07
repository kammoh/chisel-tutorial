// See LICENSE.txt for license details.
package examples


import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class FullAdderTests(c: FullAdder) extends PeekPokeTester(c, verbose = true) {
  rnd.setSeed(1478501036469L)
  for (t <- 0 until 40) {
    val a    = rnd.nextInt(2)
    val b    = rnd.nextInt(2)
    val cin  = rnd.nextInt(2)
    val res  = a + b + cin
    val sum  = res & 1
    val cout = (res >> 1) & 1

    poke(c.io.a, a)
    poke(c.io.b, b)
    poke(c.io.cin, cin)
    step(1) //////
//    println(s"sum is ${peek(c.io.sum)}")
    expect(c.io.sum, 1)
    expect(c.io.cout, cout)
  }
}

class FullAdderTester extends ChiselFlatSpec {
  behavior of "FullAdder"
  backends foreach {backend =>
    it should s"correctly add randomly generated numbers and show carry in $backend" in {
      Driver(() => new FullAdder, backend)((c) => new FullAdderTests(c)) should be (true)
    }
  }
}
