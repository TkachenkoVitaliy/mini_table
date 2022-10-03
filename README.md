

В качестве разделителя десятичной части используется точка '.'

Приложение запущенно на VPS и попробовать его можно не устанавливая локально http://176.124.192.7:8080/

Для локальной установки 
1. Склонируйте репозиторий `git clone https://github.com/TkachenkoVitaliy/mini_table.git`
2. Установите docker и docker-compose
3. Введите в терминале находясь в корневой папке проекта `docker-compose up -d`

Возможно проблема с билдом образа Backend. Для его устранения:
Добавляем в /etc/docker/daemon.json следующую строчку:
`{ "features": { "buildkit": true } }`
и рестартим docker daemon:
`sudo systemctl restart docker`