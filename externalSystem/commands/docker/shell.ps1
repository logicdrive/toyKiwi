docker run --name toykiwi_external_system_con `
    -e AWS_ACCESS_KEY=$env:AWS_ACCESS_KEY `
    -e AWS_SECRET_ACCESS_KEY=$env:AWS_SECRET_ACCESS_KEY `
    -e OPENAI_API_KEY=$env:OPENAI_API_KEY `
    -e DEEPL_API_KEY=$env:DEEPL_API_KEY `
    --rm -it --entrypoint /bin/sh `
    -p 8085:8085 sinsung6722/toykiwi_external_system:v1