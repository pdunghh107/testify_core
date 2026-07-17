# Kế hoạch Refactor Engine (Strategy Pattern) & Bổ sung Enum

## 1. Vấn đề hiện tại
- **Backend**: `DataGeneratorEngine` vi phạm SRP và OCP. Mọi logic sinh dữ liệu nằm trong một file khổng lồ với nhiều khối `if/else`, `switch/case`. Việc sinh lỗi cho Enum bị hardcode (`"INVALID_ENUM_VALUE_123"`).
- **Frontend**: `RuleBuilder` chưa có chức năng chọn và nhập danh sách giá trị cho kiểu Enum.

## 2. Giải pháp (Best Practice & SOLID)
### 2.1 Backend Kiến trúc (Strategy Pattern)
- Xây dựng một Interface `DataGeneratorStrategy` làm khuôn mẫu chung.
- Tách logic thành các class chuyên biệt:
  - `StringGeneratorStrategy` (Xử lý String, Email, UUID, Phone, Regex).
  - `NumberGeneratorStrategy` (Xử lý Integer, Number).
  - `BooleanGeneratorStrategy` (Xử lý Boolean).
  - `EnumGeneratorStrategy` (Xử lý Enum).
- Khối Engine chính (`RuleBasedDataGeneratorEngine`) sẽ trở thành một Orchestrator. Nó duyệt qua tất cả các field, tìm Strategy phù hợp và gọi hàm sinh dữ liệu.

### 2.2 Logic sinh lỗi thông minh cho Enum
- Không hardcode chuỗi báo lỗi.
- Sử dụng thư viện `DataFaker` sinh chuỗi ngẫu nhiên.
- Kiểm tra vòng lặp `while` để đảm bảo chuỗi sinh ra KHÔNG nằm trong danh sách `enumValues`.

### 2.3 Frontend Rule Builder UI
- Trong `RuleBuilder.tsx`: Thêm `Enum` vào Dropdown kiểu dữ liệu.
- Khi chọn `Enum`: Hiện ô nhập `Danh sách giá trị hợp lệ (cách nhau bằng dấu phẩy)`.
- Trong `RuleConfigPage.tsx`: Cập nhật logic Parse dữ liệu: 
  - Array (từ API) -> Chuỗi phẩy (vào UI).
  - Chuỗi phẩy (từ UI) -> Array (xuống API).
