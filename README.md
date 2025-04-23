# Android 桌面小工具

這是一個簡單的 Android 桌面小工具，提供三個按鈕來呼叫 API。

## 功能

小工具提供三個按鈕：
- ➕ +1 按鈕：呼叫 `192.168.68.90:5000/api/update-number?delta=1`
- ➖ -1 按鈕：呼叫 `192.168.68.90:5000/api/update-number?delta=-1`
- 🔄 重設為-1 按鈕：呼叫 `192.168.68.90:5000/api/set-number?value=-1`

## 佈局

小工具採用高1寬3的佈局，三個按鈕並排顯示。

## 技術實現

- 使用 `AppWidgetProvider` 實現小工具功能
- 使用 `RemoteViews` 設置小工具佈局
- 使用 `PendingIntent` 處理按鈕點擊事件
- 使用 `ExecutorService` 和 `Handler` 執行網絡請求

## 如何構建

1. 克隆此倉庫：
   ```
   git clone https://github.com/henry1266/callapk.git
   ```

2. 使用 Android Studio 開啟專案

3. 構建 APK：
   - 在 Android Studio 中，選擇「Build」>「Build Bundle(s) / APK(s)」>「Build APK(s)」
   - 構建完成後，APK 文件將位於 `app/build/outputs/apk/debug/` 目錄中

## 如何使用

1. 安裝 APK 到 Android 設備
2. 長按主屏幕空白處，選擇「小工具」
3. 找到「數值控制小工具」並添加到主屏幕
4. 點擊按鈕即可觸發相應的 API 呼叫

## 系統要求

- Android 5.0 (API 21) 或更高版本
- 需要網絡連接以呼叫 API

## 許可權

- `android.permission.INTERNET`：用於進行網絡請求

## 專案結構

- `app/src/main/AndroidManifest.xml`：應用清單文件
- `app/src/main/java/com/example/androidwidget/WidgetProvider.java`：小工具主要代碼
- `app/src/main/res/layout/widget_layout.xml`：小工具佈局文件
- `app/src/main/res/xml/widget_info.xml`：小工具配置文件
