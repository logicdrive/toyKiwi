.\venv\Scripts\activate


$env:AWS_BUCKET_NAME = "sinsung6722-toywiki"
$env:AWS_REGION_CODE = "ap-northeast-2"
cp envs/development.env .flaskenv


if (Test-Path "logs") {
    Remove-Item -Recurse -Force "logs"
}

if (Test-Path "workDirs") {
    Remove-Item -Recurse -Force "workDirs"
}


python -m flask run