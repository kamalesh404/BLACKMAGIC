# BLACKMAGIC — Professional Android Video Camera

A native Android manual video recording application inspired by the workflow, tactile manual controls, and monitoring tools of pro cinema cameras, built and tested for the **Honor 90** (Android 15 / MagicOS).

## Current Status: Phase C — Foundation

- **Platform Target**: Android 15 (API 35), Min SDK 28 (Android 9.0 Pie)
- **Language**: Kotlin 2.0.21
- **Build System**: Gradle 8.11.1 with Android Gradle Plugin 8.7.3
- **Toolchain**: Java 21 LTS
- **CI / CD**: GitHub Actions (`.github/workflows/android-build.yml`)

## Architecture Overview

```
BLACKMAGIC/
├── .github/workflows/
│   └── android-build.yml          # Automated CI: wrapper validation, test, and debug APK build
├── gradle/
│   ├── libs.versions.toml         # Version catalog for AGP, Kotlin, and dependencies
│   └── wrapper/
│       ├── gradle-wrapper.jar     # Pinned Gradle 8.11.1 wrapper
│       └── gradle-wrapper.properties
├── app/
│   ├── build.gradle.kts           # Application module build configuration
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml # Camera and audio permission declarations
│   │   │   ├── java/com/blackmagic/camera/
│   │   │   │   └── MainActivity.kt # Permission handling & initial UI
│   │   │   └── res/               # Material dark theme, layout, and drawables
│   │   └── test/
│   │       └── java/com/blackmagic/camera/
│   │           └── ExampleUnitTest.kt
├── build.gradle.kts               # Root build script
├── settings.gradle.kts            # Project settings & repository management
├── gradle.properties              # JVM options & AndroidX flags
└── .gitignore                     # Exclusion rules for build artifacts and large packages
```

## Planned Features

- Manual exposure control (ISO, Shutter speed / exposure time)
- Manual focus distance and focus assistance
- White balance and manual color temperature
- Clean cinema-style monitoring overlays (Grid, Zebra, Audio meters)
- Framerate and resolution selection tailored to device Camera2 hardware level
- H.264 / H.265 video encoding via MediaCodec / Camera2 pipeline
