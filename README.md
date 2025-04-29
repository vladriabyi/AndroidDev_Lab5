# Compass App

This application provides a digital compass interface that shows the current direction and orientation of the device.

## Author

Vladyslav Riabyi  
Group: IM-24  
Variant: 10

## Description

The Compass App is an Android application that provides a digital compass interface with the following features:

- A compass view showing the current direction with a red needle pointing to magnetic north
- A green arrow indicating the phone's current orientation
- Real-time direction updates with smooth animations
- Direction indicators in Ukrainian (Північ, Південь, Схід, Захід, etc.)
- Degree markings for precise direction reading
- Clean, dark-themed interface for better visibility

The app uses the device's magnetometer and accelerometer sensors to determine the current direction and provides a smooth, responsive user experience.

## Technical Details

The application is built using:
- Android Sensor API for compass functionality
- Custom View implementation for the compass interface
- Sensor fusion for accurate direction calculation
- Smooth animations for compass needle movement

## How to Run

1. Clone the repository to your local machine:
```bash
git clone https://github.com/vladriabyi/AndroidDev_Lab5.git
```

2. Open the project in Android Studio.

3. Build and run the application on an Android emulator or a physical device.

## Dependencies

- Android SDK
- Android Sensor API
- Android View System

## Features

- Real-time compass direction
- Phone orientation indicator
- Smooth animations
- Ukrainian language support
- Dark theme interface
- Degree markings for precise navigation
- Responsive design

## Usage

1. Launch the application
2. Hold your device flat and parallel to the ground
3. The red needle will point to magnetic north
4. The green arrow shows the direction your phone is facing
5. The text below the compass shows the current direction in degrees and cardinal points

## Notes

- For best accuracy, calibrate your device's compass before use
- Avoid using the compass near strong magnetic fields or electronic devices
- The compass requires access to the device's sensors to function properly
