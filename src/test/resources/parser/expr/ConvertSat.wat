;;; TOOL: wat2wasm
(module
  (func
    f32.const 0
    i32.trunc_s:sat/f32
    drop

    f32.const 0
    i32.trunc_u:sat/f32
    drop

    f64.const 0
    i32.trunc_s:sat/f64
    drop

    f64.const 0
    i32.trunc_u:sat/f64
    drop

    f32.const 0
    i64.trunc_s:sat/f32
    drop

    f32.const 0
    i64.trunc_u:sat/f32
    drop

    f64.const 0
    i64.trunc_s:sat/f64
    drop

    f64.const 0
    i64.trunc_u:sat/f64
    drop))