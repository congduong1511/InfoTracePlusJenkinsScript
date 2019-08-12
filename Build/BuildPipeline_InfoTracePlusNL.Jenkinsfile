// 命名規則
// ジョブ名: BuildInfoTracePlus {Product名}
def BUILDERROR = 'OFF'
pipeline {
	agent any
	options {
		skipDefaultCheckout true
	}
	parameters{
		string(name: 'ITPVERSION', defaultValue: '3225', description: 'ビルドするITPのバージョンを指定します。')
		string(name: 'BETA', defaultValue: 'Beta0', description: 'Beta番号を入力します。')
		booleanParam(name: 'ISSINGLEJOB', defaultValue: 'true', description: 'このジョブを単体で実行する場合はtrueに設定してください。')
		booleanParam(name: 'ISREBUILD', defaultValue: 'false', description: 'ビルドをリビルドで実行する場合はtrueに設定してください。')
	}
	environment {
		// Product(Project)名を定義する。略称で入力すること（例: EC, ITなど）
		PRODUCT = 'NL'
		GITURL = "git://na-git.rdd.soliton.co.jp/git/InfoTracePlus${PRODUCT}.git"
		BUILDPATH = "${ITPGITFOLDER}\\Build\\Script\\Build\\List${ITPVERSION}"
		// LogはJenkinsのworkspace\Log内にプロジェクトごとに作成する
		BUILDLOG = "${LOGFOLDERPATH}\\BuildLog_${BETA}_${PRODUCT}_${BUILD_TIMESTAMP}.log"
		LOGCOPYPATH = "\\\\galaxy\\SolitonSmart\\Build\\Log\\SSS\\${ITPVERSION}\\${BETA}\\"
	}
	stages{
		stage('PrepareJob'){
			steps{
				script{
					// git clone
					if(ISSINGLEJOB.toBoolean()){
						dir("${SCRIPTPATH}"){
							git([url: "${SCRIPTGITURL}", branch: 'master'])
						}
						dir("${ITPGITFOLDER}"){
							git([url: "${ITPGITURL}", branch: 'master'])
						}
					}
					// create log folder 
					if (!fileExists("${LOGFOLDERPATH}")) bat "chcp 65001 && mkdir \"${LOGFOLDERPATH}\""
					// delete log file
					if (fileExists("${BUILDLOG}")) bat "chcp 65001 && del \"${BUILDLOG}\""
					// script file load
					msbuildbat = load "${SCRIPTPATH}\\Build\\msbuild.groovy"
					devenv = readProperties file: "${SCRIPTPATH}\\environment.properties"
					mailbody = load "${SCRIPTPATH}\\mailbody.groovy"
				}
			}
		}
		// solutionを定義する。config, solutionファイルを指定する。ステージ名は任意で。 例: 'ReleaseStaticECBuild'
		stage('ReleaseNLSSSShared') { 
			environment{
				// config設定 例: "Release Static EC"
				CONFIG = 'Release NL' 
				// ソリューション設定(.slnは不要) 例: 'SSSShared'
				SOLUTIONFILE = 'SSSShared' 
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			when{expression {return devenv['SSSShared_ReleaseNL'] == 'ON'}}
			steps{
				script{
					if("${ITPVERSION}" != '320' && "${BETA}" != "Beta0"){
						CONFIG += " ${BETA}"
					}
					def buildreturn = 0
					// ビルドを実行する。PLATFORMwin32 or x64 でWin32とx64でのビルドを行う
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMwin32, BUILDLOG, ISREBUILD.toBoolean())
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMx64, BUILDLOG, ISREBUILD.toBoolean())
					if(buildreturn < 0){
						BUILDERROR = 'ON'
						currentBuild.result = 'FAILURE'
					}
				}
			}
		}
		stage('ReleaseNLSmartOn') { 
			environment{
				// config設定 例: "Release Static EC"
				CONFIG = 'Release NL' 
				// ソリューション設定(.slnは不要) 例: 'SSSShared'
				SOLUTIONFILE = 'SmartOn' 
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			when{expression {return devenv['SmartOn_ReleaseNL'] == 'ON'}}
			steps{
				script{
					if("${ITPVERSION}" != '320' && "${BETA}" != "Beta0"){
						CONFIG += " ${BETA}"
					}
					def buildreturn = 0
					// ビルドを実行する。PLATFORMwin32 or x64 でWin32とx64でのビルドを行う
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMwin32, BUILDLOG, ISREBUILD.toBoolean())
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMx64, BUILDLOG, ISREBUILD.toBoolean())
					if(buildreturn < 0){
						BUILDERROR = 'ON'
						currentBuild.result = 'FAILURE'
					}
				}
			}
		}
		stage('ReleaseNLBuild') { 
			environment{
				// config設定 例: "Release Static EC"
				CONFIG = 'Release' 
				// ソリューション設定(.slnは不要) 例: 'SSSShared'
				SOLUTIONFILE = 'NL' 
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			when{expression {return devenv['NL_Release'] == 'ON'}}
			steps{
				script{
					if("${ITPVERSION}" != '320' && "${BETA}" != "Beta0"){
						CONFIG += " ${BETA}"
					}
					def buildreturn = 0
					// ビルドを実行する。PLATFORMwin32 or x64 でWin32とx64でのビルドを行う
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMwin32, BUILDLOG, ISREBUILD.toBoolean())
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMx64, BUILDLOG, ISREBUILD.toBoolean())
					if(buildreturn < 0){
						BUILDERROR = 'ON'
						currentBuild.result = 'FAILURE'
					}
				}
			}
		}
		// ビルド終了後、LOGをgalaxyへコピーする
		stage('LOGCOPY'){
			steps{
				script{
					try{
						bat "xcopy /V /Q /R /Y /I \"${BUILDLOG}\" \"${LOGCOPYPATH}\""
					}catch(e){

					}
				}
			}
		}
		// 単体でのビルド終了後、BUILDERRORがあった場合はエラーメールを送信する.
		stage('MAILSEND'){
			environment{
				// BUILDERRMAILTO{Product名} Script.gitのenvironment.propertiesで定義する
				MAILTO="${devenv['BUILDERRMAILTONL']}"
			}
			when{expression { return ("${BUILDERROR}" == 'ON' &&  ISSINGLEJOB.toBoolean() == true)}}
			steps{
				script{
					wrap([$class: 'BuildUser']) {
						BUILDUSER = "${BUILD_USER_ID:'jenkins'}"
						if(MAILTO != ""){
							subject = "ビルドエラー ${PRODUCT}"
							body = mailbody.BuildMailBody("${BUILDUSER}", "${PRODUCT}", "${LOGCOPYPATH}");
						   	mail to: "${MAILTO}", subject: "${subject}", body: "${body}"
						}
					}
				}
			}
		}
	}
	post { 
		always { 
			cleanWs()
			dir("${env.WORKSPACE}@tmp") {
				deleteDir()
			}
		}
	}
}
