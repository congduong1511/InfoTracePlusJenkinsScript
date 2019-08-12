def MsBuild(msbuild, solution, solutionfile, config, platform, buildlog, isReBuild=false){
	def flag = 0
	def target = "";
	if(isReBuild) target = "/t:rebuild";
	else target = "/t:build";

	bat "chcp 65001"
	bat "echo -------------------------- >> \"${buildlog}\""
	bat "echo %date% %time% >> \"${buildlog}\""
	bat "echo \"Solution Build Start - ${solutionfile}.sln (${config}|${platform})\" >> \"${buildlog}\""
	bat "echo -------------------------- >> \"${buildlog}\""

	try{
		bat "chcp 65001"
		// MSBUILD: 環境変数(Wiki参照)
		bat "\"${MSBUILD}\" \"${solution}\" ${target} /p:Configuration=\"${config}\";Platform=\"${platform}\" >> \"${buildlog}\" 2>&1"
		bat "echo Solution Build Successed! >> \"${buildlog}\""
	}
	catch(Exception e){
		flag = -1
		bat "echo Solution Build Failed! >> \"${buildlog}\""
	}
	finally{
		bat "echo -------------------------- >> \"${buildlog}\""
		bat "echo \"Solution Build End - ${solutionfile}.sln (${config}|${platform})\" >> \"${buildlog}\""
		return flag
	}
}

return this