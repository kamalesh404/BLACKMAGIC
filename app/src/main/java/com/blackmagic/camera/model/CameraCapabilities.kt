package com.blackmagic.camera.model

/**
 * Camera2 hardware support levels supported by the application.
 */
enum class HardwareLevel {
    LEGACY,
    LIMITED,
    FULL,
    LEVEL_3;

    fun supportsManualSensor(): Boolean = this == FULL || this == LEVEL_3
}

/**
 * Supported video encoding formats.
 */
enum class VideoCodec(val mimeType: String, val displayName: String) {
    H264("video/avc", "AVC / H.264"),
    H265_HEVC("video/hevc", "HEVC / H.265")
}

/**
 * Common cinema recording resolutions.
 */
data class VideoResolution(
    val width: Int,
    val height: Int,
    val label: String
) {
    val totalPixels: Int get() = width * height
    val aspectRatio: String get() = "${width / gcd(width, height)}:${height / gcd(width, height)}"

    private fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

    companion object {
        val UHD_4K = VideoResolution(3840, 2160, "4K UHD (3840x2160)")
        val FHD_1080P = VideoResolution(1920, 1080, "1080p FHD (1920x1080)")
        val HD_720P = VideoResolution(1280, 720, "720p HD (1280x720)")
    }
}

/**
 * Sensor manual exposure and sensitivity boundary limits.
 */
data class ExposureLimits(
    val minIso: Int,
    val maxIso: Int,
    val minExposureNs: Long,
    val maxExposureNs: Long
) {
    fun clampIso(iso: Int): Int = iso.coerceIn(minIso, maxIso)
    fun clampExposure(nanos: Long): Long = nanos.coerceIn(minExposureNs, maxExposureNs)
}

/**
 * Cinema capture recording profile configuration.
 */
data class CameraProfile(
    val resolution: VideoResolution,
    val targetFps: Int,
    val codec: VideoCodec,
    val bitrateMbps: Int
) {
    companion object {
        /**
         * Calculate optimal target bitrate in Mbps based on resolution, framerate, and codec efficiency.
         */
        fun calculateOptimalBitrate(
            resolution: VideoResolution,
            fps: Int,
            codec: VideoCodec
        ): Int {
            val baseMbps = when {
                resolution.totalPixels >= VideoResolution.UHD_4K.totalPixels -> 60
                resolution.totalPixels >= VideoResolution.FHD_1080P.totalPixels -> 24
                else -> 10
            }
            val fpsFactor = if (fps > 30) 1.5 else 1.0
            val codecEfficiency = if (codec == VideoCodec.H265_HEVC) 0.75 else 1.0

            return (baseMbps * fpsFactor * codecEfficiency).toInt()
        }
    }
}
