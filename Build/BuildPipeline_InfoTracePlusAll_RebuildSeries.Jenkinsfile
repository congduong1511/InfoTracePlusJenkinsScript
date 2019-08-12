def BUILDERRPROJECT=""
pipeline{
	agent any
	options {
		skipDefaultCheckout true
	}
	parameters{
		string(name: 'ITPVERSION', defaultValue: '3225', description: 'ビルドするITPのバージョンを指定します。')
		string(name: 'BETA', defaultValue: 'Beta0', description: 'Beta番号を入力します。')
		booleanParam(name: 'ISSINGLEJOB', defaultValue: 'true', description: 'このジョブを単体で実行する場合はtrueに設定してください。')
		booleanParam(name: 'ISREBUILD', defaultValue: 'true', description: 'ビルドをリビルドで実行する場合はtrueに設定してください。')
	}
	environment{
		LOGCOPYPATH = "\\\\galaxy\\SolitonSmart\\Build\\Log\\SSS\\${ITPVERSION}\\${BETA}\\"
	}
	stages{
		stage('Prepare'){
			steps{
				script{
					if(ISSINGLEJOB.toBoolean()){
						dir("${SCRIPTPATH}"){
							git([url: "${SCRIPTGITURL}", branch: 'master'])
						}
						dir("${ITPGITFOLDER}"){
							git([url: "${ITPGITURL}", branch: 'master'])
						}
					}
					devenv = readProperties file: "${SCRIPTPATH}\\environment.properties"
					mailbody = load "${SCRIPTPATH}\\mailbody.groovy"
				}
			}
		}
		// ECとITは競合が少ないため、並行で行う
		stage('ECandITBuildProject'){
			parallel{
				stage('ECBuild'){
					when { expression { return env.BETA == devenv['BETA_EC']} }
					steps{
						script{
							try{
								build job: 'BuildInfoTracePlusEC',
								parameters: [
									string(name: "ITPVERSION", value: ITPVERSION),
									string(name: "BETA", value: BETA),
									booleanParam(name: 'ISSINGLEJOB', value: 'false'),
									booleanParam(name: 'ISREBUILD', value: ISREBUILD)
								]
							}catch(err){
								BUILDERRPROJECT+="EC,"
								echo "Build Failed!"
							}
						}
					}
				}
				stage('ITBuild'){
					when { expression { return env.BETA == devenv['BETA_IT']} }
					steps{
						script{
							try{
								build job: 'BuildInfoTracePlusIT',
								parameters: [
									string(name: "ITPVERSION", value: ITPVERSION),
									string(name: "BETA", value: BETA),
									booleanParam(name: 'ISSINGLEJOB', value: 'false'),
									booleanParam(name: 'ISREBUILD', value: ISREBUILD)
								]
							}catch(err){
								BUILDERRPROJECT+="IT,"
								echo "Build Failed!"
							}
						}
					}
				}
			}
		}
		stage('SDMCBuild'){
			when { expression { return env.BETA == devenv['BETA_SDMC']} }
			steps{
				script{
					try{
						build job: 'BuildInfoTracePlusSDMC',
						parameters: [
							string(name: "ITPVERSION", value: ITPVERSION),
							string(name: "BETA", value: BETA),
							booleanParam(name: 'ISSINGLEJOB', value: 'false'),
							booleanParam(name: 'ISREBUILD', value: ISREBUILD)
						]
					}catch(err){
						BUILDERRPROJECT+="SDMC,"
						echo "Build Failed!"
					}
				}
			}
		}
		stage('SMCBuild'){
			when { expression { return env.BETA == devenv['BETA_SMC']} }
			steps{
				script{
					try{
						build job: 'BuildInfoTracePlusSMC',
						parameters: [
							string(name: "ITPVERSION", value: ITPVERSION),
							string(name: "BETA", value: BETA),
							booleanParam(name: 'ISSINGLEJOB', value: 'false'),
							booleanParam(name: 'ISREBUILD', value: ISREBUILD)
						]
					}catch(err){
						BUILDERRPROJECT+="SMC,"
						echo "Build Failed!"
					}
				}
			}
		}
		stage('SPSBuild'){
			when { expression { return env.BETA == devenv['BETA_SPS']} }
			steps{
				script{
					try{
						build job: 'BuildInfoTracePlusSPS',
						parameters: [
							string(name: "ITPVERSION", value: ITPVERSION),
							string(name: "BETA", value: BETA),
							booleanParam(name: 'ISSINGLEJOB', value: 'false'),
							booleanParam(name: 'ISREBUILD', value: ISREBUILD)
						]
					}catch(err){
						BUILDERRPROJECT+="SPS,"
						echo "Build Failed!"
					}
				}
			}
		}
		stage('ToolsBuild'){
			when { expression { return env.BETA == devenv['BETA_TOOLS']} }
			steps{
				script{
					try{
						build job: 'BuildInfoTracePlusTools',
						parameters: [
							string(name: "ITPVERSION", value: ITPVERSION),
							string(name: "BETA", value: BETA),
							booleanParam(name: 'ISSINGLEJOB', value: 'false'),
							booleanParam(name: 'ISREBUILD', value: ISREBUILD)
						]
					}catch(err){
						BUILDERRPROJECT+="Tools,"
						echo "Build Failed!"
					}
				}
			}
		}
		stage('SmartOnBuild'){
			when { expression { return env.BETA == devenv['BETA_SO']} }
			steps{
				script{
					try{
						build job: 'BuildInfoTracePlusSmartOn',
						parameters: [
							string(name: "ITPVERSION", value: ITPVERSION),
							string(name: "BETA", value: BETA),
							booleanParam(name: 'ISSINGLEJOB', value: 'false'),
							booleanParam(name: 'ISREBUILD', value: ISREBUILD)
						]
					}catch(err){
						BUILDERRPROJECT+="SmartOn,"
						echo "Build Failed!"
					}
				}
			}
		}
		stage('NLBuild'){
			when { expression { return env.BETA == devenv['BETA_NL']} }
			steps{
				script{
					try{
						build job: 'BuildInfoTracePlusNL',
						parameters: [
							string(name: "ITPVERSION", value: ITPVERSION),
							string(name: "BETA", value: BETA),
							booleanParam(name: 'ISSINGLEJOB', value: 'false'),
							booleanParam(name: 'ISREBUILD', value: ISREBUILD)
						]
					}catch(err){
						BUILDERRPROJECT+="NL,"
						echo "Build Failed!"
					}
				}
			}
		}
		stage('SRMBuild'){
			when { expression { return env.BETA == devenv['BETA_SRM']} }
			steps{
				script{
					try{
						build job: 'BuildInfoTracePlusSRM',
						parameters: [
							string(name: "ITPVERSION", value: ITPVERSION),
							string(name: "BETA", value: BETA),
							booleanParam(name: 'ISSINGLEJOB', value: 'false'),
							booleanParam(name: 'ISREBUILD', value: ISREBUILD)
						]
					}catch(err){
						BUILDERRPROJECT+="SRM,"
						echo "Build Failed!"
					}
				}
			}
		}
		stage('IntegrateBuild'){
			when { expression { return env.BETA == devenv['BETA_INST']} }
			steps{
				script{
					try{
						build job: 'BuildInfoTracePlusIntegrate',
						parameters: [
							string(name: "ITPVERSION", value: ITPVERSION),
							string(name: "BETA", value: BETA),
							booleanParam(name: 'ISSINGLEJOB', value: 'false'),
							booleanParam(name: 'ISREBUILD', value: ISREBUILD)
						]
					}catch(err){
						BUILDERRPROJECT+="Integrate,"
						echo "Build Failed!"
					}
				}
			}
		}
		stage('BUILDERRORMAIL'){
			environment{
				// BUILDERRMAILTO Script.gitのenvironment.propertiesで定義する
				// 複数指定する場合は,(半角コンマ、スペースなし)でつなぐこと
				MAILTO="${devenv['BUILDERRMAILTO']}"
			}
			when{ expression {return BUILDERRPROJECT != ""} }
			steps{
				script{
					wrap([$class: 'BuildUser']) {
						BUILDUSER = "${BUILD_USER_ID:'jenkins'}"
						if(MAILTO != ""){
							subject = "ビルドエラー AllBuild"
							body = mailbody.BuildMailBody("${BUILDUSER}", "${BUILDERRPROJECT}", "${LOGCOPYPATH}");
						   	mail to: "${MAILTO}", subject: "${subject}", body: "${body}"
						}
					}
				}
			}
		}
	}
}