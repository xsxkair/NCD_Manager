java -jar F:\AndroidWork\MyAndroidKey\FriendlyARM-S5P6818\android-platform-key-files\signapk.jar F:\AndroidWork\MyAndroidKey\FriendlyARM-S5P6818\android-platform-key-files\platform.x509.pem F:\AndroidWork\MyAndroidKey\FriendlyARM-S5P6818\android-platform-key-files\platform.pk8 app-release.apk signed_xsx.apk

adb uninstall com.xsx.ncd.ncd_manager

adb install -r signed_xsx.apk