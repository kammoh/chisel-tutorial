// See LICENSE.txt for license details.
package solutions

import chisel3._

class VecShiftRegister extends Module {
  val io = IO(new Bundle {
    val ins   = Input(Vec(4, UInt(width = 4)))
    val load  = Input(Bool())
    val shift = Input(Bool())
    val out   = Output(UInt(width = 4))
  })
  val delays = Reg(Vec(4, UInt()))
  when (io.load) {
    delays(0) := io.ins(0)
    delays(1) := io.ins(1)
    delays(2) := io.ins(2)
    delays(3) := io.ins(3)
  } .elsewhen(io.shift) {
    delays(0) := io.ins(0)
    delays(1) := delays(0)
    delays(2) := delays(1)
    delays(3) := delays(2)
  }
  io.out := delays(3)
}
