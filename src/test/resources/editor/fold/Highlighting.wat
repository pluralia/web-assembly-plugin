(<fold text='...' expand='false'>module $color-painter
  (<fold text='...' expand='false'>export "getHeight" (func $getHeight)</fold>)
  (<fold text='...' expand='false'>export "getWidth" (func $getWidth)</fold>)
  (<fold text='...' expand='false'>export "memory" (memory $mem)</fold>)
  (<fold text='...' expand='false'>export "setDimensions" (func $setDimensions)</fold>)

  (<fold text='...' expand='false'>type $getI32 (func (result i32))</fold>)

  (<fold text='...' expand='false'>memory $mem 4</fold>)

  (<fold text='...' expand='false'>global $bytesPerPage i32 i32.const 0x10000</fold>)
  (<fold text='...' expand='false'>global $bytesPerPixel i32 i32.const 4</fold>)
  (<fold text='...' expand='false'>global $false i32 i32.const 0</fold>)
  (<fold text='...' expand='false'>global $true i32 i32.const 1</fold>)

  (<fold text='...' expand='false'>global $height (mut i32) (<fold text='...' expand='false'>i32.const 0</fold>)</fold>)
  (<fold text='...' expand='false'>global $width (mut i32) (<fold text='...' expand='false'>i32.const 0</fold>)</fold>)

  (<fold text='...' expand='false'>func $getHeight (type $getI32) (<fold text='...' expand='false'>global.set $height</fold>)</fold>)
  (<fold text='...' expand='false'>func $getWidth (type $getI32) (<fold text='...' expand='false'>global.set $width</fold>)</fold>)

  (<fold text='...' expand='false'>func $setDimentions (param $width i32) (param $height i32) (result i32)
    (local $bytes i32)
    (local $pixels i32)
    (local $pages i32)
    (local $success i32)

    (<fold text='...' expand='false'>local.set $success (global.get $true)</fold>)

    (<fold text='...' expand='false'>local.set $pixels
      (i32.mul
        (local.get $width)
        (local.get $height))</fold>)
  </fold>)
</fold>)