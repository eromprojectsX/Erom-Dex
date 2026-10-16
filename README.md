# Erom Dex

Erom Dex is an Android app for a desktop-like workspace on a phone, with the goal of running a real ARM64 Linux environment without requiring the user to type terminal commands.

## Current build

- Version: 0.3.0
- Android desktop-style shell and portrait orientation.
- File picker uses Android's Storage Access Framework.
- Browser shortcut opens the browser installed on Android.
- **Linux runtime is not yet integrated.** The current APK does not boot Debian, does not include an XFCE desktop, and cannot run Linux applications.
- Device testing on a POCO X6 Pro has not yet been performed.

## Linux implementation plan

The intended approach is a rootless ARM64 Linux userspace using a PRoot-compatible launcher, with Debian ARM64 as the first distribution. The app will need to package or safely obtain a compatible Android executable, download and verify the root filesystem, extract it to app-private storage, and launch a graphical session. A graphical display layer (for example, an embedded VNC client or another Android-compatible display path) is also required before a full Linux desktop can be claimed.

The official Termux PRoot-Distro project demonstrates that Linux userspace distributions can run on Android without root, but it depends on a working PRoot binary and supporting environment. This repository has not yet integrated those components. See https://github.com/termux/proot-distro.

## Build

The GitHub Actions workflow builds the Android debug APK and uploads it as a workflow artifact. A successful compile confirms Android build success only; it does not prove that the Linux runtime works.

## Device target

Initial target: POCO X6 Pro (Android). Performance is governed by Android's scheduling and thermal controls; the app cannot force unrestricted CPU frequency or disable thermal limits.
