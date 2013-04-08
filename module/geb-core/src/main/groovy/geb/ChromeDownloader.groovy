package geb

class ChromeDownloader extends Downloader {

    String getDefaultUrl() {
        'http://chromedriver.googlecode.com/files/'
    }

    String getDefaultExecutable() {
        'chromedriver'
    }

    String getFileName() {
        "chromedriver_${config.platform}_${config.version}.zip"
    }

    void setSystemProperties() {
        System.setProperty('webdriver.chrome.driver', executableFile.absolutePath)
    }

    String getNamespace() {
        'chrome'
    }
}
