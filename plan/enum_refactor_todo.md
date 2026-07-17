# Todo List: Refactor Engine & Bổ sung Enum

## Phase 1: Backend Refactoring (Strategy Pattern)
- [x] Tạo interface `DataGeneratorStrategy.java`.
- [x] Xây dựng `StringGeneratorStrategy` (hỗ trợ string, email, uuid, regex).
- [x] Xây dựng `NumberGeneratorStrategy` (hỗ trợ integer, number).
- [x] Xây dựng `BooleanGeneratorStrategy`.
- [x] Xây dựng `EnumGeneratorStrategy` với tính năng chặn hardcode.
- [x] Refactor `RuleBasedDataGeneratorEngine.java` để nạp và điều hướng các Strategy này.

## Phase 2: Nâng cấp Sinh lỗi Enum thông minh
- [x] Trong `EnumGeneratorStrategy`, dùng DataFaker sinh giá trị ngẫu nhiên.
- [x] Cài đặt logic kiểm tra: Nếu giá trị ngẫu nhiên trùng với `enumValues` hợp lệ thì sinh lại cho đến khi khác biệt hoàn toàn (Dùng để sinh Negative Case).

## Phase 3: Frontend Rule Builder (Enum UI)
- [x] Trong `RuleBuilder.tsx`: Thêm option `"Enum"` vào Dropdown kiểu dữ liệu.
- [x] Hiện thị Input `enumValuesStr` (nhập chuỗi ngăn cách bởi dấu phẩy) khi chọn Enum.
- [x] Cập nhật `RuleConfigPage.tsx`: Chuyển đổi qua lại giữa `enumValuesStr` (String - Frontend) và `enumValues` (Array - Backend API).
- [x] Kiểm tra toàn bộ luồng tạo và sinh dữ liệu.
