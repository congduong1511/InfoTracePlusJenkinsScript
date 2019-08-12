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
		PRODUCT = 'EC'
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
		stage('ReleaseStaticECBuild') {
			environment{
				CONFIG = "Release Static EC"
				SOLUTIONFILE = 'SSSShared'
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			when{expression {return devenv['SSSShared_ReleaseStaticEC'] == 'ON'}}
			steps{
				script{
					if("${ITPVERSION}" != '320' && "${BETA}" != "Beta0"){
						CONFIG += " ${BETA}"
					}
					def buildreturn = 0
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMwin32, BUILDLOG, ISREBUILD.toBoolean())
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMx64, BUILDLOG, ISREBUILD.toBoolean())
					if(buildreturn < 0){
						BUILDERROR = 'ON'
						currentBuild.result = 'FAILURE'
					}
				}
			}
		}
		stage('ReleaseECBuild') {
			environment{
				CONFIG = "Release EC"
				SOLUTIONFILE = 'SSSShared'
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			when{expression {return devenv['SSSShared_ReleaseEC'] == 'ON'}}
			steps{
				script{
					if("${ITPVERSION}" != '320' && "${BETA}" != "Beta0"){
						CONFIG += " ${BETA}"
					}
					def buildreturn = 0
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMwin32, BUILDLOG, ISREBUILD.toBoolean())
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMx64, BUILDLOG, ISREBUILD.toBoolean())
					if(buildreturn < 0){
						BUILDERROR = 'ON'
						currentBuild.result = 'FAILURE'
					}
				}
			}
		}
		stage('ReleaseInstBuild') {
			environment{
				CONFIG = "Release Inst"
				SOLUTIONFILE = 'SSSShared'
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			when{expression {return devenv['SSSShared_ReleaseInst'] == 'ON'}}
			steps{
				script{
					if("${ITPVERSION}" != '320' && "${BETA}" != "Beta0"){
						CONFIG += " ${BETA}"
					}
					def buildreturn = 0
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMwin32, BUILDLOG, ISREBUILD.toBoolean())
					if(buildreturn < 0){
						BUILDERROR = 'ON'
						currentBuild.result = 'FAILURE'
					}
				}
			}
		}
		stage('ECCmnReleaseBuild') {
			environment{
				CONFIG = "Release"
				SOLUTIONFILE = 'ECCmn'
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			when{expression {return devenv['ECCmn_Release'] == 'ON'}}
			steps{
				script{
					if("${ITPVERSION}" != '320' && "${BETA}" != "Beta0"){
						CONFIG += " ${BETA}"
					}
					def buildreturn = 0
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMwin32, BUILDLOG, ISREBUILD.toBoolean())
					if(buildreturn < 0){
						BUILDERROR = 'ON'
						currentBuild.result = 'FAILURE'
					}
				}
			}
		}
		stage('ECMgrReleaseBuild') {
			environment{
				CONFIG = "Release"
				SOLUTIONFILE = 'ECMgr'
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			when{expression {return devenv['ECMgr_Release'] == 'ON'}}
			steps{
				script{
					if("${ITPVERSION}" != '320' && "${BETA}" != "Beta0"){
						CONFIG += " ${BETA}"
					}
					def buildreturn = 0
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMwin32, BUILDLOG, ISREBUILD.toBoolean())
					if(buildreturn < 0){
						BUILDERROR = 'ON'
						currentBuild.result = 'FAILURE'
					}
				}
			}
		}
		stage('ECCtrlReleaseBuild') {
			environment{
				CONFIG = "Release"
				SOLUTIONFILE = 'ECCtrl'
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			when{expression {return devenv['ECCtrl_Release'] == 'ON'}}
			steps{
				script{
					if("${ITPVERSION}" != '320' && "${BETA}" != "Beta0"){
						CONFIG += " ${BETA}"
					}
					def buildreturn = 0
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMwin32, BUILDLOG, ISREBUILD.toBoolean())
					if(buildreturn < 0){
						BUILDERROR = 'ON'
						currentBuild.result = 'FAILURE'
					}
				}
			}
		}
		stage('ECConReleaseBuild') {
			environment{
				CONFIG = "Release"
				SOLUTIONFILE = 'ECCon'
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			when{expression {return devenv['ECCon_Release'] == 'ON'}}
			steps{
				script{
					if("${ITPVERSION}" != '320' && "${BETA}" != "Beta0"){
						CONFIG += " ${BETA}"
					}
					def buildreturn = 0
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMwin32, BUILDLOG, ISREBUILD.toBoolean())
					if(buildreturn < 0){
						BUILDERROR = 'ON'
						currentBuild.result = 'FAILURE'
					}
				}
			}
		}
		stage('ECImptReleaseBuild') {
			environment{
				CONFIG = "Release"
				SOLUTIONFILE = 'ECImpt'
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			when{expression {return devenv['ECImpt_Release'] == 'ON'}}
			steps{
				script{
					if("${ITPVERSION}" != '320' && "${BETA}" != "Beta0"){
						CONFIG += " ${BETA}"
					}
					def buildreturn = 0
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
				MAILTO="${devenv['BUILDERRMAILTOEC']}"
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
