package org.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesLoader {

    private Properties properties;

    public PropertiesLoader(String propertyFileName) {
        this.properties = new Properties();
        generateProperty(propertyFileName);
    }

    /**
     * Загрузка данных из файла
     *
     * @param propertyFileName - имя .properties файла
     */
    public void generateProperty(String propertyFileName) {
        try {
            InputStream is = new FileInputStream(propertyFileName);
            properties.load(is);
        } catch (FileNotFoundException ex) {
            log.error("Некорректный путь до файла {} \n", propertyFileName);
            Assert.fail(ex.getMessage());
        } catch (IOException ex) {
            log.error("Не удалось загрузить проперти: {}", ex.getMessage());
            Assert.fail(ex.getMessage());
        }
    }

    /**
     * Получение значение параметра по его имени
     *
     * @param propertyName - имя параметре
     * @return значение параметра
     */
    public String getProperty(String propertyName) throws RuntimeException {
        log.info("Получение значения параметра {}", propertyName);
        if (!properties.containsKey(propertyName)) {
            throw new RuntimeException(String.format("Параметр %s отсутствует", propertyName));
        }
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue == null || propertyValue.isEmpty()) {
            throw new RuntimeException(String.format("Значение параметра %s не задано", propertyName));
        }
        return propertyValue;
    }

    /**
     * Получение значение параметра, значение которого может быть не задано, по его имени
     *
     * @param propertyName - имя параметре
     * @return значение параметра
     */
    public String getPropertyCanBeNull(String propertyName) {
        log.info("Получение значения параметра {}", propertyName);
        if (!properties.containsKey(propertyName)) {
            log.info("Параметр {} отсутствует", propertyName);
        }
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue == null || propertyValue.trim().isEmpty()) {
            log.info("Значение параметра {} не задано", propertyName);
        }
        return propertyValue;
    }

}
