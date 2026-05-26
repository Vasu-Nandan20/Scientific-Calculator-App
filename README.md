# 📱 Numix: High-Performance Scientific Suite

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-purple.svg)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack_Compose-blue.svg)](https://developer.android.com/jetpack/compose)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://www.android.com/)

**Numix** is a premium, high-performance mathematical suite designed for engineers, scientists, and students. Built with **Jetpack Compose** and **Clean Architecture**, it combines a sophisticated scientific engine with a world-class user interface.

---

## 🌟 Key Pillars

### 🔢 Advanced Scientific Engine
- **mXparser Integration**: High-precision evaluation of complex mathematical expressions.
- **Dynamic Trig Units**: Seamless switching between Degrees and Radians with automatic function mapping (`sind`, `cosd`).
- **Inverse Operations**: Toggle-able `inv` mode for arc-trigonometric and advanced exponential functions.

### 📈 Interactive Visualization
- **Function Plotting**: Custom-built `Canvas` engine for real-time plotting of $y = f(x)$.
- **Gestural Control**: Smooth pinch-to-zoom and multi-directional panning for deep exploration of mathematical curves.

### 🔄 Multi-Category Unit Converter
- **10+ Professional Categories**: From standard Length/Weight to technical Power, Pressure, and Speed.
- **Base Conversion System**: Native support for Binary, Octal, Decimal, and Hexadecimal conversions.
- **Offline Precision**: Hardcoded, high-accuracy conversion factors ensuring zero latency and privacy.

---

## 🏗️ Technical Architecture & Design

### 🏛️ Clean Architecture (MVVM)
- **Domain Logic**: Decoupled `ExpressionEvaluator` and `ConverterLogic` for maximum testability.
- **State Management**: Reactive UI updates powered by `StateFlow` and `ViewModel`.
- **UI Layer**: 100% declarative UI using Jetpack Compose with custom Glassmorphism components.

### 🎨 Premium UI/UX Features
- **Glassmorphic Design**: Modern translucent surfaces with neon accents and subtle shadows.
- **Haptic Feedback**: Tactile interaction design for a physical-calculator feel.
- **Adaptive Navigation**: Fluid transitions between app modules via a coordinated Bottom Navigation system.

---

## 🛠️ Tech Stack & Skills Showcased

- **Kotlin Coroutines**: Asynchronous state handling.
- **Jetpack Navigation**: Type-safe navigation between deep-linked screens.
- **Material 3**: Implementation of the latest Material Design standards.
- **Canvas API**: Custom low-level drawing for the graphing engine.
- **Custom Theming**: Coordinated Dark/Light mode support with dynamic color mapping.

---

## 📦 Installation & Usage

1. **Clone the Repo**
   ```bash
   git clone https://github.com/Vasu-Nandan20/Scientific-Calculator-App.git
   ```
2. **Open in Android Studio**
   Ensure you are using **Android Studio Ladybug (2024.2.1)** or newer.
3. **Build & Run**
   Sync Gradle and deploy to your device.

---

## 🤝 Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

---

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

**Developed with ❤️ by [Vasu Nandan](https://github.com/Vasu-Nandan20)**
