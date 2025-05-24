# 🎯 partner-client

JavaFX клиент для взаимодействия с REST-сервисом `partner-service`.

---

## 📦 Структура проекта

<details> <summary>Развернуть дерево</summary>

'''partner-client/
├── .idea/                            # Конфигурационные файлы IntelliJ IDEA (не загружать в Git)
├── .mvn/                             # Настройки Maven Wrapper
│   └── wrapper/
│       └── maven-wrapper.properties
├── mvnw                              # Скрипт запуска Maven (Linux/Mac)
├── mvnw.cmd                          # Скрипт запуска Maven (Windows)
├── pom.xml                           # Главный файл конфигурации Maven (зависимости, плагины)
├── README.md                         # Документация проекта
└── src/
    └── main/
        ├── java/
        │   └── com/example/partnerclient/
        │       ├── controller/                # Контроллеры JavaFX
        │       │   ├── MainController.java        # Главная форма: список партнёров, действия
        │       │   ├── AddPartnerController.java  # Форма добавления партнёра
        │       │   └── EditPartnerController.java # Форма редактирования партнёра
        │       ├── model/
        │       │   └── Partner.java               # Модель партнёра (id, name, phone и т.д.)
        │       ├── service/
        │       │   └── PartnerService.java        # HTTP-клиент для взаимодействия с REST API
        │       ├── util/
        │       │   └── DialogUtil.java            # Всплывающие окна ошибок/уведомлений
        │       └── PartnerClientApplication.java  # Главный класс запуска JavaFX-приложения
        └── resources/
            └── com/example/partnerclient/
                ├── main-view.fxml         # Главная форма (список партнёров + кнопки)
                ├── add-partner.fxml       # Форма добавления нового партнёра
                ├── edit-partner.fxml      # Форма редактирования существующего партнёра
                └── style.css              # Стили для интерфейса (оформление кнопок, окон и т.д.)'''
</details>

---

## 📘 Описание компонентов

### 🔹 PartnerClientApplication.java
Главная точка входа. Запускает JavaFX-приложение и загружает `main-view.fxml`.

---

### 📁 controller/

#### ▶ MainController.java
Управляет главной формой. Отображает список партнёров, реагирует на действия пользователя: обновление, удаление, переход к формам добавления и редактирования.

#### ▶ AddPartnerController.java
Управляет формой добавления партнёра. Проверяет введённые данные, отправляет POST-запрос на сервер. Показывает ошибки через всплывающие окна.

#### ▶ EditPartnerController.java
Отвечает за форму редактирования партнёра. Загружает текущие данные, даёт возможность изменить их и отправляет PUT-запрос.

---

### 📁 model/

#### ▶ Partner.java
Модель данных. Представляет объект партнёра с полями:
- `id`
- `name`
- `director`
- `address`
- `phone`
- `fax`

---

### 📁 service/

#### ▶ PartnerService.java
Клиент для REST API. Выполняет HTTP-запросы (GET, POST, PUT, DELETE) к серверу `partner-service`. Используется в контроллерах.

---

### 📁 util/

#### ▶ DialogUtil.java
Утилита для отображения всплывающих окон об ошибках и уведомлениях. Помогает улучшить UX.

---

## 🖼️ FXML-файлы (интерфейс)

### ▶ main-view.fxml
Главная форма. Отображает таблицу с партнёрами и кнопки действий.

### ▶ add-partner.fxml
Форма добавления нового партнёра.

### ▶ edit-partner.fxml
Форма редактирования выбранного партнёра.

---

## 🎨 style.css
Файл со стилями для кнопок управления окном и оформления приложения.

---
