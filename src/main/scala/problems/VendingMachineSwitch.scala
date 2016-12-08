// See LICENSE.txt for license details.
package problems

import chisel3.core._
import chisel3.util._

class VendingMachineSwitch extends Module {
  val io = IO(new Bundle {
    val nickel = Input(Bool())
    val dime   = Input(Bool())
    val valid  = Output(Bool())
  })


  // coke == 20 cents


  val s_idle :: s_5 :: s_10 :: s_15 :: s_ok :: Nil = Enum(5)

  val state = Reg(init = s_idle) // 3 bits
  
  switch (state) {
    is (s_idle) {
      when (io.nickel) { state := s_5 } // 5 cents
      when (io.dime) { state := s_10 }  // 10 cents
    }
    is(s_5){
      when(io.nickel) {state := s_10}
      when(io.dime) {state := s_15}
    }
    // Fill out the remaining states here
  }

  io.valid := (state === s_ok)
}
