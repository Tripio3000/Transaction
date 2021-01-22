### Операции с транзакциями
Есть 5 пользователей со 100 рублями на счету. Пока у кого то на счету не будет остаток 0 рублей, пользователи начинают совершать транзакции случайному получателю в размере 10 рублей. Когда у пользователя не осталось денег на счету, программа завершается.

Чтобы создать базу данных с 5 пользователями и начальными деньгами на счетах запустите `SaveDataToDB.java`

Таблица в БД будет выглядеть примерно так:
```
 id | amount | version 
----+--------+---------
  1 |    100 |       0
  2 |    100 |       0
  3 |    100 |       0
  4 |    100 |       0
  5 |    100 |       0
(5 rows)
```
Чтобы начать выполнение транзакций запустите `QueryWithSession.java`. В консоль будут выводиться логи операций.

```
18:32:28.048959 - pool-1-thread-3 - [Transaction updated..., Transac{version=0, id=3, amount=100}]
18:32:28.048959 - pool-1-thread-2 - [Transaction updated..., Transac{version=0, id=2, amount=100}]
18:32:28.048959 - pool-1-thread-4 - [Transaction updated..., Transac{version=0, id=4, amount=100}]
18:32:28.048959 - pool-1-thread-1 - [Transaction updated..., Transac{version=0, id=1, amount=100}]
18:32:28.051369 - pool-1-thread-5 - [Transaction updated..., Transac{version=0, id=5, amount=100}]
18:32:28.058319 - pool-1-thread-3 - [Transaction updated..., Transac{version=1, id=3, amount=90}]
18:32:28.058352 - pool-1-thread-1 - [Transaction updated..., Transac{version=2, id=1, amount=100}]
18:32:28.058307 - pool-1-thread-2 - [Transaction updated..., Transac{version=2, id=2, amount=100}]
18:32:28.059736 - pool-1-thread-4 - [Transaction updated..., Transac{version=3, id=4, amount=110}]
18:32:28.059903 - pool-1-thread-5 - [Transaction updated..., Transac{version=2, id=5, amount=100}]
18:32:28.063690 - pool-1-thread-3 - [Transaction updated..., Transac{version=2, id=3, amount=80}]
...
```

В конечном итоге наша таблица пользователей будет выглядеть примерно так:

```
 id | amount | version 
----+--------+---------
  2 |     60 |      96
  1 |    100 |      84
  5 |    290 |      81
  4 |      0 |     128
  3 |     50 |     105
(5 rows)
```