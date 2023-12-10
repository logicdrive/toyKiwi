import uuid
import os
import boto3

from ..._global.logger import CustomLogger
from ..._global.logger import CustomLoggerType

AWS_ACCESS_KEY = os.environ.get("AWS_ACCESS_KEY")
AWS_SECRET_ACCESS_KEY = os.environ.get("AWS_SECRET_ACCESS_KEY")
AWS_BUCKET_NAME = os.environ.get("AWS_BUCKET_NAME")
AWS_REGION_CODE = os.environ.get("AWS_REGION_CODE")


CLIENT = boto3.Session(
    aws_access_key_id=AWS_ACCESS_KEY,
    aws_secret_access_key=AWS_SECRET_ACCESS_KEY,
)
CLIENT_S3 = CLIENT.resource("s3")


# 임의의 파일명을 이용해서 주어진 환경변수에 맞는 버킷에 파일을 업로드시키고, 경로를 반환하기 위해서
def uploadToPublicS3(filePath:str) -> str :
    objectKey = str(uuid.uuid4()) + "." + filePath.split(".")[-1]

    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to upload file", "<filePath: {}, objectKey: {}>".format(filePath, objectKey))
    CLIENT_S3.meta.client.upload_file(Filename=filePath, Bucket=AWS_BUCKET_NAME, Key=objectKey)

    CustomLogger.debug(CustomLoggerType.EFFECT, "Try to access public object")
    obj = CLIENT_S3.Object(AWS_BUCKET_NAME, objectKey)
    obj.Acl().put(ACL="public-read")

    uploadUrl:str = f"https://{AWS_BUCKET_NAME}.s3.{AWS_REGION_CODE}.amazonaws.com/{objectKey}"
    CustomLogger.debug(CustomLoggerType.EFFECT, "File was successfully uploaded to S3", "<uploadUrl: {}>".format(uploadUrl))
    return uploadUrl

# 주어진 버킷에 있는 해당 키를 가진 파일을 삭제시키기 위해서
def deleteToPublic3(objectKey:str) -> None :
    CLIENT_S3.Object(AWS_BUCKET_NAME, objectKey).delete()
