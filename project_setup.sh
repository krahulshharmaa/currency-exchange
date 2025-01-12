echo $LATEST_ALL_JKS_BASE64 > latest_all.jks.b64
base64 -d latest_all.jks.b64 >> latest_all.jks

echo $GOOGLE_SERVICES_BASE64 > gsj.b64
base64 -d gsj.b64 >> /home/Jetpack_MVVM_architecture/app/google-services.json