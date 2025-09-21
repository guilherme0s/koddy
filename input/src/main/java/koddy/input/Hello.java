package koddy.input;

import koddy.jni.loader.JniNativeLoader;

public class Hello {

  static {
    JniNativeLoader.load();
  }

  public static native String sayHello();
}
