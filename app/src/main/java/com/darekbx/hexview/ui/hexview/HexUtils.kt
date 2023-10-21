package com.darekbx.hexview.ui.hexview

object HexUtils {

    fun byteToHex(byte: Byte) =
        String.format("%02X ", byte)

    fun bytesToHex(bytes: ByteArray) =
        bytes.joinToString(" ") { byte -> String.format("%02X", byte) }

}