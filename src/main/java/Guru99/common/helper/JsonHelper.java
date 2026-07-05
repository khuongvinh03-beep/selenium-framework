package vn.edu.vitacademy.common.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.vitacademy.model.Config;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JsonHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHelper.class);
    private Map<String, String> locatorsMap = new HashMap<>();

    public JsonHelper(String fileName) {
        loadJsonFile(fileName);
    }

    private void loadJsonFile(String fileName) {
        String resourcePath = "object_repository/" + fileName;
        LOGGER.info("Loading locators JSON from classpath resource: {}", resourcePath);
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                LOGGER.error("Resource not found in classpath: {}", resourcePath);
                return;
            }
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            locatorsMap = gson.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), type);
            LOGGER.info("Successfully loaded {} locators from JSON: {}", 
                locatorsMap != null ? locatorsMap.size() : 0, resourcePath);
        } catch (Exception e) {
            LOGGER.error("Failed to load or parse JSON file: {}. Root cause: {}", fileName, e.getMessage());
        }
    }

    public String getLocator(String key) {
        if (locatorsMap == null || !locatorsMap.containsKey(key)) {
            LOGGER.warn("Locator key '{}' not found in JSON repository.", key);
            return "";
        }
        return locatorsMap.get(key);
    }

    public static Config readConfig(String fileName) {
        LOGGER.info("Loading config JSON from classpath resource: {}", fileName);
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                LOGGER.warn("Config resource not found in classpath: {}, using default Config values", fileName);
                return new Config();
            }
            Gson gson = new Gson();
            Config config = gson.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), Config.class);
            LOGGER.info("Successfully loaded config from JSON: {}", fileName);
            return config != null ? config : new Config();
        } catch (Exception e) {
            LOGGER.error("Failed to load or parse config JSON file: {}. Root cause: {}", fileName, e.getMessage());
            return new Config();
        }
    }

    /**
     * Đọc nội dung từ một tệp JSON thông thường nằm trên đĩa cứng (không nằm trong Resources/Classpath).
     * Rất hữu ích để đọc dữ liệu động được sinh ra trong quá trình chạy automation (ví dụ: user_credentials.json).
     *
     * @param filePath Đường dẫn tương đối hoặc tuyệt đối tới tệp JSON
     * @param clazz Kiểu class đối tượng cần chuyển đổi (ví dụ: UserCredential.class)
     * @return Đối tượng kiểu T đại diện cho dữ liệu JSON, hoặc null nếu xảy ra lỗi
     */
    public static <T> T readJsonFile(String filePath, Class<T> clazz) {
        File file = new File(filePath);
        if (!file.exists()) {
            LOGGER.info("File JSON không tồn tại tại: {}", file.getAbsolutePath());
            return null;
        }
        try (InputStream inputStream = new FileInputStream(file);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, clazz);
        } catch (Exception e) {
            LOGGER.error("Lỗi khi đọc file JSON: {}. Chi tiết: {}", filePath, e.getMessage());
            return null;
        }
    }

    /**
     * Ghi một đối tượng Java thành tệp JSON ở ổ đĩa cứng (lưu ngoài resources để có quyền ghi trong lúc chạy kiểm thử).
     * Dữ liệu JSON ghi ra sẽ được định dạng đẹp (pretty print) để người dùng dễ đọc.
     *
     * @param filePath Đường dẫn lưu tệp JSON
     * @param obj Đối tượng Java cần chuyển thành chuỗi JSON và lưu lại
     */
    public static void writeJsonFile(String filePath, Object obj) {
        try (OutputStream outputStream = new FileOutputStream(filePath);
             OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(obj, writer);
            LOGGER.info("Ghi dữ liệu vào file JSON thành công: {}", filePath);
        } catch (Exception e) {
            LOGGER.error("Lỗi khi ghi dữ liệu vào file JSON: {}. Chi tiết: {}", filePath, e.getMessage());
        }
    }
}
