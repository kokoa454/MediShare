# MediShare

あなたと家族のための薬管理Webアプリケーション  
服薬記録、リマインダー、家族への通知機能を備えたヘルスケアサポートツールです。

---

## 📋 主な機能

- ユーザー登録 / ログイン
- 薬の登録・検索（約15,000件の正式名称対応）
- 服薬スケジュール管理（例：朝食前、昼食後、15時など柔軟に指定可能）
- 家族への通知（LINE / メール通知）
- Elasticsearch によるあいまい検索（Elasticsearchが起動できない場合はMySQLによるあいまい検索に切り替え）
- PWA対応
- モバイル・PC 両対応のレスポンシブUI

---

## ⚙️ 技術スタック

| 分類         | 使用技術                                      |
|--------------|-----------------------------------------------|
| フロントエンド | HTML / CSS / Thymeleaf / JavaScript |
| バックエンド   | Spring Boot (Java 17) / Spring Security / JPA / Python（「くすりのしおり」スクレイピング及びデータベース格納用）  |
| データベース   | MySQL 8.0 |
| 検索エンジン   | Elasticsearch 9.0.4 |
| 通知・連携    | Gmail SMTP / LINE API |
| インフラ       | Nginx / Oracle Cloud Infrastructure / HTTPS対応 |

---

## 👨‍💻 開発メンバー

- rainier1007：バックエンド / セキュリティ 等
- kokoa454：フロントエンド / バックエンド / セキュリティ / UI / インフラ 等

## 📄 ライセンス

このプロジェクトは学習目的で作成されたものであり、開発者以外の運用は想定していません。
