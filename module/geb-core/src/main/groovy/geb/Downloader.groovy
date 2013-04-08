package geb

import org.apache.commons.lang3.SystemUtils

abstract class Downloader {

    static String DEFAULT_PATH = 'target/driver/'

    def ant

    File driverDir
    File executableFile

    DownloaderConfig config

    void downloadAndInstallDriver(DownloaderConfig config) {
        this.config = config
        downloadDriver(namespace, fileName, downloadUrl, executablePath)
        setSystemProperties()
    }

    void installDriver(String url, String fileName, File driverDir, File executable) {
        if (!executable) {
            ant.get(src: url + fileName, dest: fileName)
            ant.unzip(src: fileName, dest: driverDir)
            ant.delete(file: fileName)
        }
        ant.chmod(file: executable, perm: '700')
    }

    String getDownloadUrl() {
        normalizeUrl(config.url ?: defaultUrl)
    }

    abstract String getDefaultUrl()
    abstract String getDefaultExecutable()
    abstract String getFileName()
    abstract void setSystemProperties()
    abstract String getNamespace()

    String getExecutablePath() {
        normalizeExecutable(config.executable ?: defaultExecutable)
    }

    void downloadDriver(String namespace, String fileName, String url, String executablePath) {
        driverDir = getInstallDir(namespace)
        executableFile = new File(driverDir, normalizeExecutable(executablePath))
        installDriver(url, fileName, driverDir, executableFile)
    }

    File getInstallDir(String driverNamespace) {
        new File(normalize(config.path ?: DEFAULT_PATH), normalize(driverNamespace))
    }

    String normalize(String path) {
        String normalizedPath = path.endsWith('/') ? path : path + '/'
        normalizedPath.startsWith('/') ? normalizedPath.substring(1, -1) : normalizedPath
    }

    String normalizeExecutable(String path) {
        path.startsWith('/') ? path.substring(1, -1) : path
    }

    String normalizeUrl(String url) {
        url.endsWith('/') ?: url + '/'
    }

    String getPlatform() {
        if (SystemUtils.IS_OS_MAC) return macPlatform
        if (SystemUtils.IS_OS_WINDOWS) return windowsPlatform
        if (SystemUtils.IS_OS_LINUX) return linuxPlatform
    }

    String getMacPlatform() {
        'mac'
    }

    String getWindowsPlatform() {
        'win'
    }

    String getLinuxPlatform() {
        String arch = System.getProperty('sun.arch.data.model')
        if (arch == '64') return 'linux64'
        else return 'linux32'
    }

}
