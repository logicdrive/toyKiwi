sed "s/AWS_ACCESS_KEY_VALUE/$AWS_ACCESS_KEY/g" ./kubernetes/deployment.yaml |\
sed "s/AWS_SECRET_ACCESS_KEY_VALUE/$AWS_SECRET_ACCESS_KEY/g" |\
sed "s/OPENAI_API_KEY_VALUE/$OPENAI_API_KEY/g" |\
sed "s/DEEPL_API_KEY_VALUE/$DEEPL_API_KEY/g" |\
kubectl apply -f -