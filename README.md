# RSOI2
## Лабораторные работы по РСОИ
![Travis-ci](https://api.travis-ci.org/dayannn/RSOI2.svg)
[![codecov](https://codecov.io/gh/dayannn/RSOI2/branch/master/graph/badge.svg)](https://codecov.io/gh/dayannn/RSOI2)

### Настройка БД

```sql
$psql -h localhost -U postgres
CREATE database users_db;
CREATE database books_db;
CREATE database reviews_db;
CREATE role program WITH password 'test';
GRANT ALL PRIVILEGES ON database users_db TO program;
GRANT ALL PRIVILEGES ON database books_db TO program;
GRANT ALL PRIVILEGES ON database reviews_db TO program;
ALTER role program WITH login;
/q
```
___

### Запросы
#### Сервис книг

Получить список всех книг
```
curl -X GET "localhost:15150/books
```

Добавить книгу
```
curl -X POST -i --header "Content-type: application/json" -d "{"name":"Chippolino", "pages_num":68}" "localhost:15150/books"
```

#### Сервис отзывов

Получить список всех отзывов
```
curl -X GET "localhost:15151/reviews"
```


Добавить отзыв
```
curl -X POST -i --header "Content-type: application/json" -d "{"text":"Very good book", "uid":1, "bookId":2}" "localhost:15151/reviews"
```

