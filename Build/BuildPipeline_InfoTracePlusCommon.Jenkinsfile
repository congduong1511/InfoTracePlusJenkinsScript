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
		PRODUCT = 'Common'
		// GITURL = "git://na-git.rdd.soliton.co.jp/git/InfoTracePlus${PRODUCT}.git"
		BUILDPATH = "${ITPGITFOLDER}\\Build\\Script\\Build\\List${ITPVERSION}"
		BUILDLOG = "${LOGFOLDERPATH}\\BuildLog_${BETA}_${PRODUCT}_${BUILD_TIMESTAMP}.log"
		LOGCOPYPATH = "\\\\galaxy\\SolitonSmart\\Build\\Log\\SSS\\${ITPVERSION}\\${BETA}\\"
	}
	stages{
		stage('PrepareJob'){
			steps{
				script{
					// git clone
					// ジョブ単体実行の場合はgit cloneを行い、そうでない場合はスルー
					if(ISSINGLEJOB.toBoolean()){
						dir("${SCRIPTPATH}"){
							git([url: "${SCRIPTGITURL}", branch: 'master'])
						}
						//dir("${ITPGITFOLDER}"){
							//git([url: "${ITPGITURL}", branch: 'master'])
						//}
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
		// stage('NetAttestSFReleaseBuild') {
		// 	environment{
		// 		SOLUTIONFILE = 'NetAttestSF'
		// 		CONFIG = "Release"
		// 		SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
		// 	}
		// 	steps{
		// 		script{
		// 			def buildreturn = 0
		// 			buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMx86, BUILDLOG, ISREBUILD.toBoolean())
		// 			buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMx64, BUILDLOG, ISREBUILD.toBoolean())
		// 			if(buildreturn < 0){
		// 				BUILDERROR = 'ON'
		// 				currentBuild.result = 'FAILURE'
		// 			}
		// 		}
		// 	}
		// }
		stage('PolicyServerReleaseBuild') {
			environment{
				SOLUTIONFILE = 'PolicyServer'
				CONFIG = "Release"
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			steps{
				script{
					def buildreturn = 0
					// ここのビルドプラットフォームはPolicyServer.slnの設定によりx86を指定しています
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMx86, BUILDLOG, ISREBUILD.toBoolean())
					buildreturn += msbuildbat.MsBuild(MSBUILD, SOLUTION, SOLUTIONFILE, CONFIG, PLATFORMx64, BUILDLOG, ISREBUILD.toBoolean())
					if(buildreturn < 0){
						BUILDERROR = 'ON'
						currentBuild.result = 'FAILURE'
					}
				}
			}
		}
		stage('SSSSharedReleaseStaticECBuild') {
			environment{
				SOLUTIONFILE = 'SSSShared'
				CONFIG = "Release Static EC"
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			steps{
				script{
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
		stage('SSSSharedReleaseSSSAgtBuild') {
			environment{
				SOLUTIONFILE = 'SSSShared'
				CONFIG = "Release SSSAgt"
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			steps{
				script{
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
		stage('SSSSharedReleaseBuild') {
			environment{
				SOLUTIONFILE = 'SSSShared'
				CONFIG = "Release"
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			steps{
				script{
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
		stage('ECSharedReleaseBuild') {
			environment{
				SOLUTIONFILE = 'ECShared'
				CONFIG = "Release"
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			steps{
				script{
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
		stage('ECSharedUnicodeStaticReleaseBuild') {
			environment{
				SOLUTIONFILE = 'ECShared'
				CONFIG = "Unicode Static Release"
				SOLUTION = "${BUILDPATH}\\${SOLUTIONFILE}.sln"
			}
			steps{
				script{
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
				// 複数指定する場合は,(半角コンマ、スペースなし)でつなぐこと
				MAILTO="${devenv['BUILDERRMAILTOCOMMON']}"
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
	//post { 
	//	always { 
	//		cleanWs()
	//		dir("${env.WORKSPACE}@tmp") {
	//			deleteDir()
	//		}
	//	}
	//}
}
