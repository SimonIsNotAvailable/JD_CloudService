# Дипломная работа “Облачное хранилище”

## Описание проекта
Задача — разработать REST-сервис. Сервис должен предоставить REST-интерфейс для загрузки файлов и вывода списка уже загруженных файлов пользователя.

Все запросы к сервису должны быть авторизованы. Заранее подготовленное веб-приложение (FRONT) должно подключаться к разработанному сервису без доработок, а также использовать функционал FRONT для авторизации, загрузки и вывода списка файлов пользователя.
Полная задача по по [ссылке](https://github.com/netology-code/jd-homeworks/blob/master/diploma/cloudservice.md)

## Технологии, использованные в проекте
- Spring boot
- Spring Security
- Maven
- Docker
- JUnit
- Mockito 
- TestContainers (пока еще нет)
- Flyway
- Lombok
- PostgreSQL

## Запуск приложения
В терминале, находясь в корневой директории проекта, необходимо ввести команду:  
```docker-compose up``` (пока ещё нет)
В результате в соответствии с Dockerfile будет создан и запущен контейнер, содержащий в себе REST-сервис,
а также БД PostreSQL. 
Приложение доступно по адресу http://localhost:80 и логин в приложение под пользователем `user@mail.com` c паролем `USER` 