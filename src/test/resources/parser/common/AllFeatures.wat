;;; TOOL: wat2wasm
;;; ARGS: --enable-all
(module
  ;; threads
  (memory 1 1)

  ;; mutable globals
  (global (mut i32) (i32.const 0))

  (func (result i32)
    ;; mutable globals
    i32.const 0
    global.set 0

    ;; saturating float-to-int
    f32.const 0
    i32.trunc_sat32_s
    drop

    ;; sign extention
    i32.const 0
    i32.extend8_s
    drop

    ;; threads
    i32.const 0
    i32.const 0
    i32.atomic.rmw.add
    drop

    ;; multi-value
    block (result i32 i32)
      i32.const 0
      i32.const 0
    end
    drop
    drop

    ;; call
    call_indirect (type $f)
  )
)