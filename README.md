### log-aggregator
#### Uwagi:
* Metoda do dodawania logu /log/register przyjmuje jako parametr 'application' nazwę aplikacji, z której logi pochodzą (w związku z wymogiem pobierania danych dla konkretnej aplikacji). Same logi są pobierane jako body wiadomości post w formie czystego stringu, gdzie każda linijka to nowy log.
* Metoda do pobierania logów /log/get przyjmuje kilka parametrów:
  * application - nazwa aplikacji, z której logi chcemy dostać
  * startDate - data i/lub czas w formacie yyyy-MM-ddTHH:mm:ss.[SSS], HH:mm:ss.[SSS], lub yyyy-MM-dd; brak daty lub czasu zaowocuje poszukiwaniem w bazie danych logów z datą lub czasem równymi null i rozpatrywaniem jedynie drugiej z wartości
  * endDate - data i/lub czas w formacie jak wyżej; założyłem, że nikt nie będzie na tyle głupi, żeby podawać te argumenty w różnych formatach, a jeśli będzie, to sam będzie sobie winny
  * xml - true, jeśli chcemy dostać dane w formie xml-a, false lub brak parametru zaowocuje zwróceniem json-a
  * page - numer strony
  * pageSize - rozmiar strony
* Zgodnie z zaleceniem robiłem commity co jakiś czas, jednak proszę wziąć pod uwagę, że te pośrednie mają jedynie pokazać proces myślowy, nie stanowić o jakości kodu jako takiej, ponieważ z zasady robię commit i push dopiero, gdy sam jestem już w pełni zadowolony z rozwiązania zadania, jakie zostało mi powierzone
