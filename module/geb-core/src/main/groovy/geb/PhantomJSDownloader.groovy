package geb

class PhantomJSDownloader extends Downloader {

    String getDefaultUrl() {
        return 'https://phantomjs.googlecode.com/files/'
    }

    String getDefaultExecutable() {
        return "phantomjs-${config.version}-${config.platform}/bin/phantomjs"
    }

    String getFileName() {
        return "phantomjs-${config.version}-${config.platform}.zip"
    }

    void setSystemProperties() {
        System.setProperty('phantomjs.binary.path', executableFile.absolutePath)
    }

    String getNamespace() {
        'phantomjs'
    }

    @Override
    String getMacPlatform() {
        'macosx'
    }

    @Override
    String getWindowsPlatform() {
        'windows'
    }

    String getLinuxPlatform() {
        String arch = System.getProperty('sun.arch.data.model')
        if (arch == '64') return 'linux-x86'
        else return 'linux-i686'
    }
}
