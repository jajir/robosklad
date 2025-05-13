# RoboSklad


* Nemel jsem cas vsechno dukladne otestovat
* Pouze mam overene, ze umim neobratne poslat/prijmout zpravu z java.
* Mozna to pojede na win, ale nema jak to otestovat

Prerequisities:
- linux
- docker
- java 21, pouze pokud chcete zkouset java clienta



# Jak jsem to zkousel
## Start stop Artemis serveru
```shell
cd artemis
./start.sh
./stop.sh
```
- poprve musite oba scripty udelat spustitelne `chmod 755 ./start.sh`.
Pokud jste startem spustili podivejte se na http://localhost:8161/console/artemis. Pristup je `admin`/`admin`. Je to nejake provozni view a potvrzeni, ze vse jede.
- Pokud chcete vyzkouset java clienta. Otevrte `java-stomp-client` v Visual Studio Code. Spustte jedny test, co tam je: StompClientApplicationTests
- Na consoli by melo byt neco jako `recieved message: ...`
- Hura poslali jste prvni message :-)



Linky:
- podivejte se na https://stomp.github.io/implementations.html, vypada to jako celkem udrzovany seznam knihoven.
- Artemis documentation https://activemq.apache.org/components/artemis/documentation/latest/index.html
