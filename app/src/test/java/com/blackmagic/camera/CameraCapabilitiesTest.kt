package com.blackmagic.camera

import com.blackmagic.camera.model.CameraProfile
import com.blackmagic.camera.model.ExposureLimits
import com.blackmagic.camera.model.HardwareLevel
import com.blackmagic.camera.model.VideoCodec
import com.blackmagic.camera.model.VideoResolution
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CameraCapabilitiesUnitTest {

    @Test
    fun hardwareLevel_manualSensorSupport_isAccurate() {
        assertFalse(HardwareLevel.LEGACY.supportsManualSensor())
        assertFalse(HardwareLevel.LIMITED.supportsManualSensor())
        assertTrue(HardwareLevel.FULL.supportsManualSensor())
        assertTrue(HardwareLevel.LEVEL_3.supportsManualSensor())
    }

    @Test
    fun videoResolution_aspectRatio_isCorrect() {
        assertEquals("16:9", VideoResolution.UHD_4K.aspectRatio)
        assertEquals("16:9", VideoResolution.FHD_1080P.aspectRatio)
        assertEquals("16:9", VideoResolution.HD_720P.aspectRatio)
        assertEquals(3840 * 2160, VideoResolution.UHD_4K.totalPixels)
    }

    @Test
    fun exposureLimits_clamping_functionsCorrectly() {
        val limits = ExposureLimits(
            minIso = 100,
            maxIso = 3200,
            minExposureNs = 100_000L,       // 1/10000s
            maxExposureNs = 1_000_000_000L  // 1s
        )

        assertEquals(100, limits.clampIso(50))
        assertEquals(3200, limits.clampIso(6400))
        assertEquals(800, limits.clampIso(800))

        assertEquals(100_000L, limits.clampExposure(50_000L))
        assertEquals(1_000_000_000L, limits.clampExposure(2_000_000_000L))
        assertEquals(500_000_000L, limits.clampExposure(500_000_000L))
    }

    @Test
    fun cameraProfile_bitrateCalculation_reflectsEfficiency() {
        val h264Bitrate4k = CameraProfile.calculateOptimalBitrate(
            VideoResolution.UHD_4K,
            30,
            VideoCodec.H264
        )
        val h265Bitrate4k = CameraProfile.calculateOptimalBitrate(
            VideoResolution.UHD_4K,
            30,
            VideoCodec.H265_HEVC
        )

        assertEquals(60, h264Bitrate4k)
        assertEquals(45, h265Bitrate4k) // H.265 requires ~25% less bandwidth
        assertTrue(h265Bitrate4k < h264Bitrate4k)

        val h264Bitrate1080p = CameraProfile.calculateOptimalBitrate(
            VideoResolution.FHD_1080P,
            30,
            VideoCodec.H264
        )
        assertEquals(24, h264Bitrate1080p)
    }
}
