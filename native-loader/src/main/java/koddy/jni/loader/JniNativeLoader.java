package koddy.jni.loader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

public final class JniNativeLoader {

  private static final String LIBRARY_NAME = "native";
  private static volatile boolean loaded;

  private JniNativeLoader() {}

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static synchronized void load() {
    if (loaded) {
      return;
    }

    String filename = mapLibraryName(LIBRARY_NAME);
    String resourcePath = "/natives/" + filename;

    try (InputStream in = JniNativeLoader.class.getResourceAsStream(resourcePath)) {
      if (in == null) {
        throw new UnsatisfiedLinkError("Native library resource not found: " + resourcePath);
      }
      Path temp = Files.createTempFile("jni-", "-" + filename);
      Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);
      // Ensure executable permission for Linux/macOS
      try {
        temp.toFile().setExecutable(true);
      } catch (Throwable ignored) {
      }
      System.load(temp.toAbsolutePath().toString());
      loaded = true;
    } catch (IOException e) {
      throw new UnsatisfiedLinkError("Failed to load native library: " + e.getMessage());
    }
  }

  private static String mapLibraryName(String base) {
    String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
    if (os.contains("win")) {
      return base + ".dll";
    } else if (os.contains("mac") || os.contains("darwin")) {
      return "lib" + base + ".dylib";
    } else {
      return "lib" + base + ".so"; // Linux and others
    }
  }
}
