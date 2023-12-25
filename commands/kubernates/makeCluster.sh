# ToyKiwi가 EKS에서 작동하기 위한 최소한의 컴퓨팅 환경을 생성시켜줌

export REGION=ap-northeast-2
export CLUSTER_NAME=usgmeta03-eks
export ROOT_ACCOUNT_UID=252337848848



echo "[*] 클러스터 생성중..."
eksctl create cluster --name $CLUSTER_NAME --version 1.27 --with-oidc --managed --node-type t3.medium --nodes 2 --nodes-min 1 --node-volume-type gp3 --nodes-max 2 --asg-access --full-ecr-access

echo "[*] Kubectl에 생성된 클러스터 등록중..."
aws eks update-kubeconfig --name $CLUSTER_NAME
kubectl get nodes



echo "[*] EKS 전용 스토리지 생성중... : 관련 IAM 계정 생성"
eksctl create iamserviceaccount \
  --override-existing-serviceaccounts \
  --region $REGION \
  --name ebs-csi-controller-sa \
  --namespace kube-system \
  --cluster $CLUSTER_NAME \
  --attach-policy-arn arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy \
  --approve \
  --role-only \
  --role-name AmazonEKS_EBS_CSI_DriverRole_$CLUSTER_NAME


echo "[*] EKS 전용 스토리지 생성중... : Customresourcedefinition, Controller 생성"
kubectl apply -f https://raw.githubusercontent.com/kubernetes-csi/external-snapshotter/master/client/config/crd/snapshot.storage.k8s.io_volumesnapshotclasses.yaml
kubectl apply -f https://raw.githubusercontent.com/kubernetes-csi/external-snapshotter/master/client/config/crd/snapshot.storage.k8s.io_volumesnapshotcontents.yaml
kubectl apply -f https://raw.githubusercontent.com/kubernetes-csi/external-snapshotter/master/client/config/crd/snapshot.storage.k8s.io_volumesnapshots.yaml

kubectl apply -f https://raw.githubusercontent.com/kubernetes-csi/external-snapshotter/master/deploy/kubernetes/snapshot-controller/rbac-snapshot-controller.yaml
kubectl apply -f https://raw.githubusercontent.com/kubernetes-csi/external-snapshotter/master/deploy/kubernetes/snapshot-controller/setup-snapshot-controller.yaml


echo "[*] EKS 전용 스토리지 생성중... : Addon 생성"
eksctl create addon --region $REGION --name aws-ebs-csi-driver --cluster $CLUSTER_NAME --service-account-role-arn arn:aws:iam::$ROOT_ACCOUNT_UID:role/AmazonEKS_EBS_CSI_DriverRole_$CLUSTER_NAME --force


echo "[*] EKS 전용 스토리지 생성중... : Storage Class 생성"
kubectl apply -f - <<EOF
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: ebs-sc
  annotations:
    storageclass.kubernetes.io/is-default-class: "true"
provisioner: ebs.csi.aws.com
volumeBindingMode: WaitForFirstConsumer
EOF

echo "[*] EKS 전용 스토리지 생성중... : Storage Class Config 세팅"
kubectl patch storageclass gp2 -p '{"metadata": {"annotations":{"storageclass.kubernetes.io/is-default-class":"false"}}}'



echo "[*] 환경 생성이 완료되었습니다."