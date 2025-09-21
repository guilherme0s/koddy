#include <jni.h>

extern "C" JNIEXPORT jstring JNICALL
Java_koddy_input_Hello_sayHello(JNIEnv* env, jclass) {
    const char* message = "Hello from C++ JNI!";
    return env->NewStringUTF(message);
}
