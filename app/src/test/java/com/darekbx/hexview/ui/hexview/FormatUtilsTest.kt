package com.darekbx.hexview.ui.hexview

import org.junit.Test

class FormatUtilsTest {

    @Test
    fun `Numbers are properly formatted`() {
        assert(FormatUtils.formatNumber(24125151) == "24 125 151")
        assert(FormatUtils.formatNumber(241125151) == "241 125 151")
        assert(FormatUtils.formatNumber(242511) == "242 511")
        assert(FormatUtils.formatNumber(951) == "951")
        assert(FormatUtils.formatNumber(51) == "51")
        assert(FormatUtils.formatNumber(2151) == "2 151")
        assert(FormatUtils.formatNumber(1241125151) == "1 241 125 151")
    }
}