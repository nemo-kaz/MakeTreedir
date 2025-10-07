
makeTreeDir.groovy をつくります

c:\ から始めて、subdirectoryを探索していきます

発見したsubdirectorの1行1行をdirectory_tree.txt に書き込みます。
書き込みをスキップする対象のsubdirectoryは
以下のものです
- そのfullpathのどれかのsubdirectoryが _, $, , ^, ~, , `, で始まっている。例えば .vscode という directoryから以下はスキャン対象外です
- そのfullpathのどこかで AppData, diffbrowser, WinSxS, System Volume Information, ProgramData, Cryptoator を含んでいる。
- アクセスできなかったdirectory。
- 文字化けして日本語でなくなっているdirectory。

処理がerrorを起こしても構わず次のdirectoryに移行します。

skipしたdirectoryは画面に表示します。


最後に起動されたdirectoryに directory_tree.txt というファイルを作成して書き出します。

書き出しformatは以下のようなものです、drive letterは書き出しません。

directory_tree.txt の中身の例
'''
/Users/icezu
/Users/icezu/Application Data
/Users/icezu/Contacts
/Users/icezu/Cookies
/Users/icezu/Desktop
/Users/icezu/Documents
/Users/icezu/Documents/My Music
/Users/icezu/Documents/My Pictures
/Users/icezu/Documents/My Videos
/Users/icezu/Documents/Office のカスタム テンプレート
'''