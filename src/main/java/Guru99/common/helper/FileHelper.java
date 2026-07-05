package vn.edu.vitacademy.common.helper;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class FileHelper {
  private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

  public static void deleteFolder(String folderPath) {
    // TODO: Xóa thư mục chỉ định
    File folder = new File(folderPath);
    if (folder.exists() && folder.isDirectory()) {
      LOGGER.info("Deleting folder: {}", folderPath);
      File[] files = folder.listFiles();
      if (files != null) {
        for (File file : files) {
          if (file.isDirectory()) {
            deleteFolder(file.getAbsolutePath());
          } else {
            if (!file.delete()) {
              LOGGER.warn("Failed to delete file: {}", file.getAbsolutePath());
            }
          }
        }
      }
      if (!folder.delete()) {
        LOGGER.warn("Failed to delete folder: {}", folderPath);
      }
    }
  }
}