def BuildMailBody(user, projects, logfolder){
        def body = "ビルドシステム（実行ユーザー: ${user}）です。\r\n以下のプロジェクトのビルドにて、エラーが発生しました。\r\n";

        projects.split(",").toList().collect{project->
                body += "・${project}\r\n";
        }
        body += "担当者は、確認・修正を行ってください。\r\n修正に時間がかかるような場合は、修正完了予定日時を連絡してください。\r\n"

        body += "ログファイルは、以下のフォルダにアップしてあります。\r\n <file:// ${logfolder}>"

        return body
}

def CollectMailBody(user, projects, logfolder){
        def body = "ビルドシステム（実行ユーザー: ${user}）です。\r\n以下のプロジェクトのコレクトにて、エラーが発生しました。\r\n";

        projects.split(",").toList().collect{project->
                body += "・${project}\r\n";
        }
        body += "担当者は、確認・修正を行ってください。\r\n修正に時間がかかるような場合は、修正完了予定日時を連絡してください。\r\n"

        body += "ログファイルは、以下のフォルダにアップしてあります。\r\n <file:// ${logfolder}>"

        return body
}

def PackMailBody(user, projects, logfolder){
        def body = "ビルドシステム（実行ユーザー: ${user}）です。\r\n以下のプロジェクトのパックにて、エラーが発生しました。\r\n";

        projects.split(",").toList().collect{project->
                body += "・${project}\r\n";
        }
        body += "担当者は、確認・修正を行ってください。\r\n修正に時間がかかるような場合は、修正完了予定日時を連絡してください。\r\n"

        body += "ログファイルは、以下のフォルダにアップしてあります。\r\n <file:// ${logfolder}>"

        return body
}

def PackCompleteBody(user, folder){
        def body = "ビルドシステム（実行ユーザー: ${user}）です。\r\nパック作業が完了しました。\r\n";
        body += "担当者は、確認・修正を行ってください。\r\n"
        body += "以下のフォルダにパックファイルがアップロードされます。\r\n <file:// ${folder}>"
        return body
}

return this