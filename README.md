# RSOI2
![Travis-ci](https://travis-ci.org/dayannn/RSOI2.svg?branch=develop)
[![codecov](https://codecov.io/gh/dayannn/RSOI2/branch/develop/graph/badge.svg)](https://codecov.io/gh/dayannn/RSOI2)

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