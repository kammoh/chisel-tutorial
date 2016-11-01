package examples

import chisel3._
import chisel3.util._

/**
  * Created by kamyar on 10/26/16.
  */
class Euler(h: Double, width: Int = 16, binaryPoint: Int = 16) extends Module {
  val io = IO(new Bundle {
    val in = Input(Decoupled(new Bundle {
      val x0 = FixedPoint(width, binaryPoint)
      val y0 = FixedPoint(width, binaryPoint)
      val a = FixedPoint(width, binaryPoint)
      val b = FixedPoint(width, binaryPoint)
      val xr = FixedPoint(width, binaryPoint)
    })
    )
    val out = Output(Decoupled(FixedPoint(width, binaryPoint)))
  })

  val hfp = FixedPoint.fromDouble(h)

  // state registers
  val done = Reg(init = false.B)
  val busy = Reg(init = false.B)

  // data-path registers
  val xr = Reg(FixedPoint(width, binaryPoint))
  val x = Reg(FixedPoint(width, binaryPoint))
  val y = Reg(FixedPoint(width, binaryPoint))
  val a = Reg(FixedPoint(width, binaryPoint))
  val b = Reg(FixedPoint(width, binaryPoint))

  // control & data-path
  when(!busy && io.in.valid) {
    x := io.in.bits.x0
    xr := io.in.bits.xr
    y := io.in.bits.y0
    a := io.in.bits.a
    b := io.in.bits.b

    done := false.B
    busy := true.B
  }

  when(busy) {
    x := x + hfp
    y := y + hfp * (a * x + b * y)

    when(x >= xr) {
      // enough
      done := true.B
      busy := false.B
    }
  }

  io.out.valid := done
  io.out.bits := y

  io.in.ready := !busy
}
