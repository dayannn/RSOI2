# RSOI2
![Travis-ci](https://travis-ci.org/dayannn/RSOI2.svg?branch=develop)
[![codecov](https://codecov.io/gh/dayannn/RSOI2/branch/develop/graph/badge.svg)](https://codecov.io/gh/dayannn/RSOI2)

### Настройка БД

```postgresql
psql -h localhost -U postgres
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