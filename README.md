# Task-Management-System
В папке проекта необходимо ввести команду docker compose up    
После создания образов, необходимо добавить интеграцию с бд в IDE для запуска скриптов инициализации бд.    

Db type: PostgreSQL 14.7    
database: task_management_system;    
user: user;    
password: 1234;    
port: 5432;    
url: jdbc:postgresql://localhost:5432/task_management_system;    

Далее, в src/main/resources необходимо запустить два скрипта, инициализирущих бд (create.sql, init.sql).     
С этого момента, сервис готов принимать запросы.    
Для запуска тестов, необходимо локально запустить класс с тестами.    
Документация доступна здесь : http://localhost:8080/swagger-ui/index.html    
Браузеры сейчас перенаправляют http на https протокол, поэтому необходимо обязательно открыть браузер в режиме инкогнито, по крайней мере, в моем случае.
