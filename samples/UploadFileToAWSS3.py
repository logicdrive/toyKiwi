# 파일을 AWS Public S3 스토리지에 업로드해보는 샘플파일

import boto3
import os

class AWSProxy :
    def __init__(self) :
        self.__CLIENT = boto3.Session(
        aws_access_key_id=os.environ["AWS_ACCESS_KEY"],
        aws_secret_access_key=os.environ["AWS_SECRET_ACCESS_KEY"],
    )
        
    def uploadToPublicS3(self, filePath:str, bucketName:str, regionCode:str="ap-northeast-2") -> str :
        objectKey = filePath.split("/")[-1]

        s3 = self.__CLIENT.resource("s3")
        s3.meta.client.upload_file(Filename=filePath, Bucket=bucketName, Key=objectKey)

        obj = s3.Object(bucketName, objectKey)
        obj.Acl().put(ACL="public-read")
        return f"https://{bucketName}.s3.{regionCode}.amazonaws.com/{objectKey}"
    
    def deleteToPublicS3(self, bucketName:str, objectKey:str) -> None :
        s3 = self.__CLIENT.resource("s3")
        s3.Object(bucketName, objectKey).delete()

awsProxy = AWSProxy()
print(awsProxy.uploadToPublicS3("./tempCutted.mp4", "sinsung6722-toywiki"))
awsProxy.deleteToPublicS3("sinsung6722-toywiki", "dcce7cfb-d7fc-4a4a-a3eb-aa71291aaf57.mp4")