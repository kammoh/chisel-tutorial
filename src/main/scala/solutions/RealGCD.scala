// See LICENSE.txt for license details.
package solutions

import chisel3._
import chisel3.util.{Valid, DeqIO}

class RealGCDInput extends Bundle {
  val a = UInt(16.W)
  val b = UInt(16.W)
}

class RealGCD extends Module {
  val io  = IO(new Bundle {
    val in  = DeqIO(new RealGCDInput())
    val out = Output(Valid(UInt(16.W)))
  })

  val x = Reg(UInt())
  val y = Reg(UInt())

  val busy = Reg(init=false.B)

  io.in.ready := !busy


  when (io.in.valid && !busy) {
    x := io.in.bits.a
    y := io.in.bits.b
    busy := true.B
  }

  when (busy) {
    when (x > y)  {  // swap
      x := y
      y := x
    } otherwise   {  // subtract
      y := y - x
    }
  }

  io.out.bits  := x

  io.out.valid := y === 0.U && busy
  when (io.out.valid) {
    busy := false.B
  }

  //io.out.valid := false.B
  //when (y === 0.U && busy) {
  //  busy := false.B
  //  io.out.valid := true.B
  //}
}
