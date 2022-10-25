#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_android_topheadlinesapp_externals_AppNativeData_getApiKey(JNIEnv *env, jclass obj) {
    return (*env)->NewStringUTF(env, "a4a687d78daf4759a6aef3803e0cec6a");
}