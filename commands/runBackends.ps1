Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./video;./commands/run.ps1"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./subtitle;./commands/run.ps1"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./externalSystemProxy;./commands/run.ps1"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./collectedData;./commands/run.ps1"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./externalSystem;./commands/run.ps1"