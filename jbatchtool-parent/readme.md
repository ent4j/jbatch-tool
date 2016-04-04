# ジョブの実行 (特定のジョブ「SimpleBatch」の実行)
curl -X POST http://localhost:8080/jbatchtool-server/rest/jbatch/start

# ジョブの実行 (任意のジョブの実行)
//curl -X GET http://localhost:8080/jbatchtool-server/rest/jbatch/job --data 'jobid=SimpleBatch'
curl -X GET http://localhost:8080/jbatchtool-server/rest/jbatch/job/SimpleBatch
curl -X GET http://localhost:8080/jbatchtool-server/rest/jbatch/job/SimpleBatch2

案２)simple
curl -X GET http://localhost:8080/jbatchtool-server/rest/batchstatus/2

curl -X GET http://localhost:8080/jbatch/rest/job/SimpleBatch

curl -X GET http://localhost:8080/jbatchtool-server/rest/jbatch/jobExecutions

curl -X GET http://localhost:8080/my-batchwar1-1.0.0-SNAPSHOT/rest/jbatch/job/SimpleBatch
curl -X GET http://localhost:8080/my-batchwar2-1.0.0-SNAPSHOT/rest/jbatch/job/SimpleBatch

# 準備
jbatchtool-cli/target/jbatchtool-cli-bin.zipを解答
jbatchtool-cli/target/jbatchtool-cli/binへ移動
chmod 755 jbatchtool-cli.sh
 ./jbatchtool-cli.sh  [ジョブ名]
 ./jbatchtool-cli.sh  SimpleBatch2

## jbatchtool-cli.shの修正案
### 修正前
./jbatchtool-cli.sh  [ジョブ名]
### 修正後(args4jによるオプション使用、ジョブへのプロパティ渡し)
./jbatchtool-cli.sh  --hostname=[ホスト名 or IPアドレス:localhost] --port=[ポート番号:8080] -username=[ユーザ名] --password=[パスワード] ¥
                 ジョブ名 key1=value1 key2=value2 key3=value3  key@=value@
                 -h=.... -p=... -u=... -i 
                 必須:
                 ジョブ名
                 オプション:
                 --hostname (-H) 
                 --hostname (-P) 
                 --username (-u) ユーザ名
                 --password (-p) パスワード
                 --identity (-i) 公開鍵ファイルのパス
                 --help (-h)
                 ジョブのプロパティ(key@=value@で指定)

# ジョブのリスト表示
http://localhost:8080/jbatchtool-server/faces/jobman.xhtml

# URLの構造
## 命名規則:
http://localhost:8080/jbatch/[コンポーネント名]/[任意のパス]

## 例1
http://localhost:8080/jbatch/faces/*.xhtml ->管理画面のURL
http://localhost:8080/jbatch/rest/job/[ジョブ名] ->ジョブの実行・停止を指示する

## 例2 (さらにURLを簡素化)
http://localhost:8080/faces/*.xhtml ->管理画面のURL
 or http://localhost:8080/jobman ->管理コンソールを参照するにあたって、ユーザはJSFで実装されたかを見せなくてよい。
http://localhost:8080/rest/job/[ジョブ名] ->ジョブの実行・停止を指示する

# プロジェクト構成の方針
ユーザはjarを作るだけが望ましい
warをビルドしない、dependencyに指定するだけにする、業務に関係ない余計なコード見せない

ユーザが使用する雛型はearを作成するものとする、作ったjarに対する依存関係をもつearをつくる

# 依存関係
jbatchtool-parent
jbatchtool-core        共通処理の定義（ジョブオペレータよりジョブのステータス取得など）
  `-jbatchtool-server  管理コンソールの定義、RESTエンドポイントの定義(warプロジェクト)
  `-jbatchtool-cli     RESTクライアントの定義、ジョブスケジューラが使用することを想定(appassembler-maven-plugin, maven-assembly-plugin)
  `                (実行体の名称jbatchtool-cli.sh,jbatchtool-cli.bat)
  `-jbatchtool-desktop GUI(JavaFXで実装)
  `-jbatchtool-mobile  GUI(iOS用,Android用)

jbatchtoolへ名称変更
jbatchtool-parent
jbatchtool-core
  `-jbatchtool-server
  `-jbatchtool-cli
  `-jbatchtool-desktop
  `-jbatchtool-mobile

# 設計方針
ビジネスロジックは独立したBeanに定義する
->複数のクライアントからJSF、REST、バッチから呼び出せることを想定（JavaFXはREST経由で取得を想定）
テストを簡素化するため、EJBでは実装しない、POJOで実装する（スコープは要確認、できれば@Dependentにする）

# 実装方法
配置先パッケージ名->org.jbatchtool.server.bean
付与するアノテーション @Dependent or @RequestScoped (javax.enterprise.context.RequestScoped)

# COBOLとjBatch

|コンポーネント|COBOL|jBatch|
|:-|-:|:-:|
|ジョブ記述言語|JCL|job.xml|
|モジュールを記述する言語|COBOL|JavaでPOJOとして実装@Dependent付与|
|ジョブスケジューラ|JES2|なし JP1やTWS、Hinemosの使用が前提|
|クライアント|TSO|jbatchsh|
|監視(CUI)|TSO|jbatchtop|
|管理コンソール|TSO|jobman|
||||


＃TODO
済 BootStrap3対応
  多言語化
済 複数ジョブ定義
済 Ajax対応
  表示項目の整理
済 jbath-cliの作成(sh経由で実行可能なjarを作成するMavenプラグイン maven-assembly-pluginを使用する)
  jbath-cliのWinodows版追加(maven-assembly-plugin platformタグにwindowsの情報を追加)
  jbath-topの作成(監視ツール)
  親pomの作成(バージョン番号、設定の統一)
  JavaFx版クライアントの作成
  ジョブのテンプレート作成1(省メモリ、並行実行)
  ジョブのテンプレート作成2(ジョブの高速化 infinispanの使用)
  ジョブのテンプレート作成3(jbatchにおけるmap reduceの表現)
  帳票ソリューションの実装
  データモデルの定石の提示
  A4ルールの提示(A4におさまらないコード、ダイアグラム、SQLは何かしら他のコンポートネントで実装すべき処理を記述しているためA4 1枚に収める)
  初期表示の実装(画面初回ロード時もジョブ一覧をチェックするように実装する、何もしないと空のまま f:viewAction)
  URLの簡素化(http://localhost:8080/jbatchtool-server/rest/jbatch/job/SimpleBatch2 -> http://localhost:8080/jbatch/rest/job/SimpleBatch2)
  jsonによる引数の引き渡し実装 http://d.hatena.ne.jp/lottz/20091010/1255161353
済 COBOLとjBatchの対応表作成
  WAS,WebLogicへの対応(JavaEEコンテナ同士が協調して、他言語からのマイグレーションを促進)　(Webコンテナ固有の機能の吸収)
  コンパイル不要な実行(job.xmlの外出し)
(中止) 主要なプラットフォームへの配布方法の準備(yum,apt,dnf,chocolatey,仮想マシンアプライアンス)
  楽観ロックのアノテーション(名前は@Optimisticを想定)による簡易な実装(lombokの拡張機能を利用)->まずはスニペットを準備
済 maven javadoc生成 javadoc:javadoc
済 maven ソース取得 dependency:sources
  JCL・COBOL・ジョブスケジューラの文脈でjBatchを解釈した説明書、サンプルを準備する
済 ear形式
  プロジェクト名変更(新規作成してソースをコピー)
  github,bintrayへの公開,
  MavenCentralへの公開(事前にNexusのアカウント作成が必要)
  RestClientCheckResultAsyncの引数変更
  