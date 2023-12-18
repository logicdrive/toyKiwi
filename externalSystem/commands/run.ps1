.\venv\Scripts\activate



cp envs/development.env .flaskenv


if (Test-Path "logs") {
    Remove-Item -Recurse -Force "logs"
}

if (Test-Path "workDirs") {
    Remove-Item -Recurse -Force "workDirs"
}


python -m flask run