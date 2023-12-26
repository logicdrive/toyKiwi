### 로컬에서 간편 실행 방법
-----
1. docker-compose.yml에서 외부 시스템에 접근하기 위한 환경설정 세팅
```yaml
  toykiwi_external_system:
    image: sinsung6722/toykiwi_external_system:v1
    ports:
      - "8085:8085"
    environment:
      AWS_ACCESS_KEY: ${AWS_ACCESS_KEY} # AWS S3 서비스를 이용하기 위한 엑세스 키 등록
      AWS_SECRET_ACCESS_KEY: ${AWS_SECRET_ACCESS_KEY} # AWS S3 서비스를 이용하기 위한 비밀키 등록
      OPENAI_API_KEY: ${OPENAI_API_KEY} # Open AI Wisper, ChatGPT 서비스를 이용하기 위한 API 키 등록
      DEEPL_API_KEY: ${DEEPL_API_KEY} # DeepL Translate 서비스를 이용하기 위한 API 키 등록
      AWS_BUCKET_NAME: "sinsung6722-toywiki" # 다운받은 동영상을 저장시킬 S3 버킷명 입력
      AWS_REGION_CODE: "ap-northeast-2" # 다운받은 동영상을 저장시킬 S3 버킷의 리전코드 입력
```

2. 다음 코드를 실행해서 마이크로 서비스들을 전부 실행
```shell
docker-compose up
```

3. 실행된 프론트엔드에 브라우저로 접속해서 정상 작동 여부 확인
```text
http://localhost:8088
```

### EKS에서 간편 실행 방법
-----
1. externalSystem/kubernetes/deployment.yaml에서 외부 시스템에 접근하기 위한 환경설정 세팅
```yaml
env:
  - name: AWS_ACCESS_KEY # AWS S3 서비스를 이용하기 위한 엑세스 키 등록
    value: AWS_ACCESS_KEY_VALUE # Host 환경 설정에서 'AWS_ACCESS_KEY' 값을 등록하기
  - name: AWS_SECRET_ACCESS_KEY # AWS S3 서비스를 이용하기 위한 비밀키 등록
    value: AWS_SECRET_ACCESS_KEY_VALUE # Host 환경 설정에서 'AWS_SECRET_ACCESS_KEY' 값을 등록하기
  - name: OPENAI_API_KEY # Open AI Wisper, ChatGPT 서비스를 이용하기 위한 API 키 등록
    value: OPENAI_API_KEY_VALUE # Host 환경 설정에서 'OPENAI_API_KEY' 값을 등록하기
  - name: DEEPL_API_KEY # DeepL Translate 서비스를 이용하기 위한 API 키 등록
    value: DEEPL_API_KEY_VALUE # Host 환경 설정에서 'DEEPL_API_KEY' 값을 등록하기
  - name: AWS_BUCKET_NAME # 다운받은 동영상을 저장시킬 S3 버킷명 입력
    value: sinsung6722-toywiki # 여기서 수정
  - name: AWS_REGION_CODE # 다운받은 동영상을 저장시킬 S3 버킷의 리전코드 입력
    value: ap-northeast-2 # 여기서 수정
```

2. 다음 코드를 실행해서 모든 서비스를 EKS에 올리기
```shell
.\commands\kubernates\initKafkaEnv.ps1 # helm 및 Kafka Repo 등록
.\commands\kubernates\installKafka.ps1 # Kafka 설치
.\commands\kubernates\services\runAll.ps1 # 모든 관련 서비스 EKS에 설치
```

3. 실행된 프론트엔드에 브라우저로 접속해서 정상 작동 여부 확인
```text
http://gateway_external_address:8088
```