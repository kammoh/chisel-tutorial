// See LICENSE.txt for license details.
package solutions

import chisel3._

class VecShiftRegisterSimple extends Module {
  val io = IO(new Bundle {
    val in  = Input(UInt(8.W))
    val out = Output(UInt(8.W))
  })

  val initValues = Seq.fill(4) { 0.U(8.W) }
  val delays = Reg(init = Vec(initValues))

  delays(0) := io.in
  delays(1) := delays(0)
  delays(2) := delays(1)
  delays(3) := delays(2)
  io.out    := delays(3)
}
