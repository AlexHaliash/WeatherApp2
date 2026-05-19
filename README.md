# WeatherApp2
# WeatherApp (Compose Multiplatform)

Кроссплатформенное приложение погоды: **Android**, **iOS**, **Desktop (Linux/Windows/macOS)** и **Web**.

Стек: Compose Multiplatform, Ktor, kotlinx.serialization, корутины, кеш (Settings + JSON).

## Быстрый старт

### Требования

- JDK **17+**
- Android SDK (для Android)
- Xcode 15+ (для iOS, только macOS)

### API-ключ OpenWeatherMap

Ключ задаётся в `gradle.properties`:

```properties
OPENWEATHER_API_KEY=87d4e4891f75644673e9e144d9b0f46c
```

Можно переопределить в `local.properties` (см. `local.properties.example`).

### Запуск без Android Studio (терминал)

Из корня проекта (`WeatherApp`):

```bash
# Windows
gradlew.bat :androidApp:installDebug
gradlew.bat :desktopApp:run
gradlew.bat :webApp:jsBrowserDevelopmentRun
gradlew.bat :webApp:wasmJsBrowserDevelopmentRun

# Linux / macOS
./gradlew :androidApp:installDebug
./gradlew :desktopApp:run
./gradlew :webApp:jsBrowserDevelopmentRun
```

| Платформа | Команда |
|-----------|---------|
| Android APK | `gradlew :androidApp:assembleDebug` |
| Desktop | `gradlew :desktopApp:run` |
| Web (JS) | `gradlew :webApp:jsBrowserDevelopmentRun` |
| Web (Wasm) | `gradlew :webApp:wasmJsBrowserDevelopmentRun` |
| iOS | Открыть `iosApp/` в Xcode → Run (после `./gradlew :shared:embedAndSignAppleFrameworkForXcode`) |

### Тесты

```bash
gradlew :shared:jvmTest
gradlew :shared:testAndroidHostTest
gradlew :shared:jsTest
gradlew :androidApp:connectedDebugAndroidTest   # нужен эмулятор/устройство
```

## GitHub Actions

Workflow: [`.github/workflows/ci.yml`](.github/workflows/ci.yml)

### Что делает CI

1. **shared-tests** — JVM и JS тесты модуля `shared`
2. **android** — сборка APK + Android host UI-тесты
3. **desktop** — пакет desktop + JVM-тесты
4. **web** — сборка JS-дистрибутива
5. **ios** (macOS) — компиляция iOS framework и simulator tests

### Как подключить к GitHub

1. Создайте **публичный** репозиторий и запушьте проект:

```bash
git init
git add .
git commit -m "WeatherApp KMP"
git remote add origin https://github.com/YOUR_USER/WeatherApp.git
git push -u origin main
```

2. (Опционально) **Settings → Secrets → Actions** → `OPENWEATHER_API_KEY` = ваш ключ.  
   Если секрет не задан, используется значение из `gradle.properties`.

3. Вкладка **Actions** → workflow **CI** запустится на push/PR.

### Submodule в репозитории лабораторной работы

В репозитории с документацией (лабораторная):

```bash
git submodule add https://github.com/YOUR_USER/WeatherApp.git app/WeatherApp
git submodule update --init --recursive
```

В отчёте укажите ссылку на публичный репозиторий и скриншот успешного CI.

## Структура

- `shared/` — UI, ViewModel, API, кеш
- `androidApp/` — Android entry point
- `desktopApp/` — Desktop (JVM)
- `webApp/` — Web (JS + Wasm)
- `iosApp/` — Xcode + SwiftUI shell

## Платформенный UI

- **Android**: Material 3 карточки с тенью, `OutlinedTextField` с иконкой поиска
- **iOS**: плоские карточки, `SearchBar`, `SegmentedButton` для городов
- **Desktop**: чёткие рамки, окно с изменяемым размером
- **Web**: адаптивная сетка 1 / 2 / 3 колонки

## Лицензия

Учебный проект.
