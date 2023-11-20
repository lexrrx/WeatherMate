# WeatherMate App

## Overview

WeatherMate is an Android application designed to provide real-time weather updates. The app consists of two primary components: `MainActivity`, the main user interface, and `WeatherService`, a background service responsible for fetching and displaying weather data.

## Features

- **Real-Time Weather Updates**: Delivers periodic weather data updates.
- **Service Interaction**: Users can start or stop the weather service via the UI.
- **Dynamic UI**: Updates in response to changes in weather data and service state.
- **Notifications**: Displays ongoing weather updates through system notifications.

## Components

### MainActivity

`MainActivity` serves as the application's main user interface. It interacts with `WeatherService` and displays weather data.

#### Key Features:
- Manages user interactions to control the weather service.
- Binds to `WeatherService` to receive weather data updates.
- Utilizes `WeatherViewModel` for state management.
- Composable UI with `HeaderControl` and `WeatherCard`.

#### Lifecycle:
- Manages binding and unbinding to `WeatherService`.
- Observes and reacts to changes in `WeatherViewModel`.

### WeatherService

`WeatherService` is a foreground service that fetches and updates weather data periodically.

#### Key Features:
- Performs periodic weather data fetches using `WeatherRepository`.
- Updates UI with current weather data via notifications.
- Manages its lifecycle and foreground service requirements.
- Stores service running state in shared preferences.

#### Lifecycle:
- Runs continuously in the background to update weather data.
- Manages notification creation and updates.

## Usage

Users interact with `MainActivity` to start or stop `WeatherService`. The service fetches weather updates and displays them in notifications, which, when tapped, navigate back to `MainActivity`.

## Technical Details

- **ViewModels and State Management**: `WeatherViewModel` is used for managing the state and interactions between `MainActivity` and `WeatherService`.
- **Service Binding**: `MainActivity` binds to `WeatherService` for direct interaction.
- **Dependency Injection**: Utilizes Koin for dependency management.
- **Coroutines**: Used in `WeatherService` for asynchronous operations.


