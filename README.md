# Банковский сервис
Реализовано всё техническое задание ниже.  
Доступ к Swagger по ссылке http://localhost:8080/swagger-ui/index.html  
Скрипт БД: resources -> scriptDb.sql (так же указать данные в application.properties)

# Техническое задание.
## Функциональные требования
**Пользователи и банковские аккаунты:**
- У каждого пользователя есть строго один “банковский аккаунт”, на котором изначально лежит какая-то сумма.
- У пользователя также есть телефон и email. Телефон и/или email обязательны.
- Дополнительные данные пользователя: дата рождения и ФИО.
- Система не имеет ролей, только обычных клиентов.
- Служебное API (с открытым доступом) позволяет создавать новых пользователей, указывая логин, пароль, изначальную сумму, телефон и email (логин, телефон и email не должны быть заняты).

**Ограничения по балансу:**
- Баланс счета клиента не может уходить в минус ни при каких обстоятельствах.

**Изменение контактных данных:**
- Пользователь может добавить/сменить свой номер телефона или email, если они еще не заняты другими пользователями.
- Пользователь не может менять остальные данные.

**API поиска:**
- Поиск можно выполнять по любому клиенту.
- Должна быть фильтрация, пагинация и сортировка.

**Фильтры:**
- Если передана дата рождения, фильтр записей, где дата рождения больше переданной в запросе.
- Если передан телефон, фильтр по 100% сходству.
- Если передано ФИО, фильтр по формату “начинается с {текст из запроса}”.
- Если передан email, фильтр по 100% сходству.
- Доступ к API должен быть аутентифицирован (кроме служебного API для создания новых клиентов).

**Начисление процентов:**
- Раз в минуту баланс каждого клиента увеличивается на 5%, но не более чем на 207% от начального депозита.  
  Пример:  
  Было: 100, стало: 105.  
  Было: 105, стало: 110.25.

**Перевод денег:**  
Реализовать функционал перевода денег с одного счета на другой.  
Перевод выполняется со счета аутентифицированного пользователя на счет другого пользователя.  
Необходимые валидации и потокобезопасность должны быть обеспечены.
## Нефункциональные требования
- OpenAPI/Swagger:
- Добавить OpenAPI/Swagger для документирования API.
- Логирование: включить логирование для отслеживания операций и ошибок.
- Аутентификация через JWT: реализовать аутентификацию с помощью JWT.
- Написать тесты для покрытия функционала трансфера денег.  
  
