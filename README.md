# Erom Dex

Erom Dex is an Android app prototype intended to provide a desktop-like workspace on Android, with a guided path toward a Linux ARM64 environment without requiring terminal commands from the user.

## Current status

- Android UI prototype: home, desktop shell, files, apps, settings, diagnostics, and Linux setup screens.
- File browsing uses Android's Storage Access Framework.
- Performance mode requests Android's sustained-performance mode where supported; it does not overclock the device.
- **A real Linux userspace/runtime is not yet integrated.** The Linux setup screen is a prototype, not a working Debian/XFCE installation.
- An installable APK must be produced by a successful Android build and tested on-device before it can be considered ready.

## Build

The full Android source archive is available separately as `EromDex-Android-Source.zip`. Extract it, open the project in Android Studio, and build the `app` debug variant.

## CI

The Android Actions workflow is intended to compile the project and publish a debug APK as a workflow artifact after the source files are committed.

## Device target

Initial target: POCO X6 Pro (Android). Compatibility and performance still require device testing.
