package examples

import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class EulerTests(c: Euler) extends PeekPokeTester(c) {
  val (a, b, x0, y0, xr, out) = (64, 48, 16)
  do {
    val first = if (t == 0) 1 else 0;
    poke(c.io.in.bits.a, a)
    poke(c.io.in.bits.b, b)
    poke(c.io.in.bits.x0, x0)
    poke(c.io.in.bits.y0, y0)
    poke(c.io.in.bits.xr, xr)
    poke(c.io.in.valid, 1)
    step(1)
  } while (t <= 1 || peek(c.io.out.valid) == 0)
  expect(c.io.out.bits, outlo)
}

class EulerTester extends ChiselFlatSpec {
  behavior of "GCD"
  backends foreach { backend =>
    it should s"correctly compute GCD of two numbers in $backend" in {
      Driver(() => new GCD, backend)(c => new EulerTests(c)) should be (true)
    }
  }
}

