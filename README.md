# MakeTreedir

ディレクトリツリーをスキャンして、テキストファイルに出力するGroovyスクリプトです。

## 概要

`makeTreeDir.groovy` は、C:\ ドライブから始まるディレクトリ構造を再帰的に探索し、すべてのディレクトリパスを `directory_tree.txt` ファイルに書き出します。システムディレクトリや特殊なディレクトリは自動的にスキップされます。

## 機能

- **再帰的ディレクトリスキャン**: C:\ から始まるすべてのサブディレクトリを探索
- **スマートフィルタリング**: システムディレクトリや特殊文字で始まるディレクトリを自動スキップ
- **エラー耐性**: アクセス拒否やその他のエラーが発生しても処理を継続
- **UTF-8出力**: 日本語を含むパスも正しく処理
- **詳細なログ**: スキップされたディレクトリとその理由を表示

## 必要要件

- Groovy (インストール済みであること)
- Apache Commons Lang3 (自動的にダウンロードされます)

## 使用方法

```bash
groovy makeTreeDir.groovy
```

スクリプトを実行すると、カレントディレクトリに `directory_tree.txt` が作成されます。

## スキップされるディレクトリ

以下の条件に該当するディレクトリはスキャン対象外となります:

### 1. 特殊文字で始まるディレクトリ
パス内のいずれかのディレクトリ名が以下の文字で始まる場合:
- `_` (アンダースコア)
- `$` (ドル記号)
- `.` (ドット)
- `^` (キャレット)
- `~` (チルダ)
- `` ` `` (バッククォート)

例: `.vscode`, `$RECYCLE.BIN`, `_cache` など

### 2. 除外対象ディレクトリ名を含むパス
パスのどこかに以下の名前を含む場合 (大文字小文字を区別しない):
- `AppData`
- `diffbrowser`
- `WinSxS`
- `System Volume Information`
- `ProgramData`
- `Cryptoator`

### 3. その他
- アクセス権限がないディレクトリ
- 読み取り不可のディレクトリ
- 文字エンコーディングの問題があるディレクトリ (非ASCII印字可能文字を含む)

## 出力形式

`directory_tree.txt` には、ドライブレターを除いた相対パスが1行に1つずつ書き出されます:

```
/Users/user
/Users/user/Contacts
/Users/user/Desktop
/Users/user/Documents
/Users/user/Documents/My Music
/Users/user/Documents/My Pictures
```

## 技術詳細

- **言語**: Groovy
- **依存関係**: Apache Commons Lang3 3.12.0 (Grape経由で自動取得)
- **文字エンコーディング**: UTF-8
- **出力ファイル**: `directory_tree.txt`

## プロジェクト構造

```
MakeTreedir/
├── makeTreeDir.groovy      # メインスクリプト
├── directory_tree.txt      # 出力ファイル (実行後に生成)
├── docs/
│   ├── application.md      # アプリケーション仕様
│   └── requirements.md     # 技術要件
└── README.md               # このファイル
```

## 注意事項

- スクリプトはC:\ ドライブ全体をスキャンするため、実行には時間がかかる場合があります
- 既存の `directory_tree.txt` は上書きされます
- スキップされたディレクトリは標準出力に表示されます
- エラーが発生しても処理は中断されず、次のディレクトリに進みます

## ライセンス

このプロジェクトのライセンスはMITです。
