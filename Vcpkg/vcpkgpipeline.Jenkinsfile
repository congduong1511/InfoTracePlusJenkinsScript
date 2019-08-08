pipeline {
    agent any
    options {
        skipDefaultCheckout true
    }
    stages {
        stage ('pull') {
            steps {
                git 'https://github.com/Microsoft/vcpkg.git'
                // git clone
                dir("${SCRIPTPATH}"){
                    git([url: "${SCRIPTGITURL}", branch: 'master'])
                }
            }
        }
        stage ('init') {
            steps {
                bat "bootstrap-vcpkg.bat"
                bat "vcpkg.exe upgrade"
                bat "vcpkg.exe update"

                // integrate install はPowerShellで実行する必要がある
                // bat "vcpkg.exe integrate install"
                bat "powershell start-process  \"${SCRIPTPATH}\\Vcpkg\\InstallVcpkg.bat\" -verb runas"
            }
        }
        stage ('installx86') {
            steps {
                // bat "vcpkg.exe install boost:x86-windows"
                bat "vcpkg.exe install boost:x86-windows-static"
                // bat "vcpkg.exe install libarchive:x86-windows"
                bat "vcpkg.exe install libarchive:x86-windows-static"
                // bat "vcpkg.exe install wtl:x86-windows"
                bat "vcpkg.exe install wtl:x86-windows-static"
                // bat "vcpkg.exe install zlib:x86-windows"
                bat "vcpkg.exe install zlib:x86-windows-static"
                bat "vcpkg.exe install winpcap:x86-windows-static"
            }
        }
        stage ('installx64') {
            steps {
                // bat "vcpkg.exe install boost:x64-windows"
                bat "vcpkg.exe install boost:x64-windows-static"
                // bat "vcpkg.exe install libarchive:x64-windows"
                bat "vcpkg.exe install libarchive:x64-windows-static"
                // bat "vcpkg.exe install wtl:x64-windows"
                bat "vcpkg.exe install wtl:x64-windows-static"
                // bat "vcpkg.exe install zlib:x64-windows"
                bat "vcpkg.exe install zlib:x64-windows-static"
                bat "vcpkg.exe install winpcap:x64-windows-static"
            }
        }
    }
}