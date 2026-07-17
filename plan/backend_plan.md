# Kế Hoạch Triển Khai Chi Tiết Backend (Testify Core)

Dựa trên toàn bộ tài liệu nghiệp vụ đồ sộ về việc sinh ma trận hoán vị kiểm thử (15+ trường, Regex, Constraints, Sinh dữ liệu động), kế hoạch này trình bày cực kỳ chi tiết từng ngóc ngách của hệ thống, bao gồm cả việc tái sử dụng **Shared Kernel** và **Kết nối Database**.

## 1. Mục Tiêu Nghiệp Vụ Chuyên Sâu
Hệ thống không chỉ bắn API đơn giản, mà là một **Test Automation Matrix Engine**. 
Nó có khả năng:
* Đọc một mẫu cấu hình `Rule` cực phức tạp chứa: Danh sách trường bắt buộc (`required_fields`), Bộ sinh dữ liệu động (`generators`), Các giới hạn (`constraints` như `min_length`, `regex`, `enum`, `date format`, `foreign_key`).
* Tự động suy luận mã lỗi: Thiếu trường bắt buộc -> Kỳ vọng `400`. Trùng lặp `email`/`identity_number` -> Kỳ vọng `409`. Enum sai -> Kỳ vọng `422/400`.
* Sinh các hoán vị (Bỏ từng trường, nhúng giá trị sai, nhúng giá trị trùng).
* Thực thi siêu tốc hàng nghìn request qua **Virtual Threads** và báo cáo log thời gian thực về Frontend qua **SSE**.

## 2. Kiến Trúc Database & Shared Kernel

### 2.1 Tích hợp Shared Kernel
Để chuẩn Enterprise và DRY, hệ thống Testify sẽ không đứng độc lập mà kế thừa từ `shared-kernel` của dự án (cụ thể là project `c:\dung\testify_core\shared-kernel`):
- **Exceptions**: Tái sử dụng `ResourceNotFoundException`, `GlobalExceptionHandler` để xử lý khi không tìm thấy Rule ID.
- **Base DTO / Base Entity**: Kế thừa các model base (created_at, updated_at).
- **Utils**: Tái sử dụng ObjectMapper utils, Security/CORS config nếu có trong Shared Kernel.

### 2.2 Kiến Trúc Database (Lưu Cấu Hình)
Thay vì đọc file JSON tĩnh, chúng ta sẽ lưu các Rule cấu hình vào Database (PostgreSQL hoặc MySQL tùy dự án đang dùng).
- **Bảng `test_rules`**:
  - `id` (UUID / BIGINT)
  - `rule_code` (VARCHAR) - ví dụ: `staff_creation_api`
  - `name` (VARCHAR)
  - `payload_schema` (JSONB / TEXT) - Chứa `required_fields`, `generators`, `constraints`.
  - Kế thừa Base Entity từ Shared Kernel.

**Cấu hình DB dự kiến (`application.yml`):**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/testify_db
    username: postgres
    password: password
  jpa:
    hibernate:
      ddl-auto: update
```
*(Lưu ý: Chỗ này sẽ điều chỉnh khớp với cấu hình DB hiện tại của Testify Core).*

## 3. Kiến Trúc Luồng Code (Chi tiết thuật toán)

### 3.1. Engine Sinh Hoán Vị Cấu Trúc (Structural Permutation)
Sử dụng thuật toán **Tập Lũy Thừa (Power Set)** bằng Bitwise Operator `(1 << n)`:
- Duyệt qua `n` trường dữ liệu của payload. 
- Nếu thiếu một trường nằm trong `required_fields`, hệ thống lập tức gắn cờ `expectedStatus = 400`.
- Nếu cấu trúc đủ `required_fields`, gắn `expectedStatus = 201/200`.

### 3.2. Engine Sinh Dữ Liệu Động (Data Generator)
Chống trùng lặp dữ liệu trong Happy Path:
- `prefix_counter`: Dùng `AtomicInteger` để tự tăng an toàn trong môi trường đa luồng (VD: `STF_1`, `STF_2`).
- `timestamp_email`: Nối Epoch Time (`staff_17123912@company.com`).
- `timestamp_numeric`: Tạo ID số duy nhất.

### 3.3. Engine Nhúng Lỗi (Constraints Injector)
Từ một payload "Sạch", duyệt qua toàn bộ `constraints` của 15+ trường:
- **String/Regex**: Tiêm `invalid_values` -> Gắn `expected = 400`.
- **Unique**: Tiêm `duplicate_value` -> Gắn `expected = 409`.
- **Enum**: Tiêm các giá trị ngoài mảng (VD: `SUPERMAN` thay vì `DIRECTOR`).
- **Number**: Tiêm giá trị nằm ngoài biên `min_value`, `max_value`.

### 3.4. Giao Tiếp Real-time & Ghi Đĩa Đa Luồng
- Mỗi Test Case là 1 task đưa vào `Executors.newVirtualThreadPerTaskExecutor()`.
- Thread gửi request, nhận response, ngay lập tức đóng gói thành `TestLogDTO` và đẩy qua `SseEmitter` về Client.
- Đồng thời, Thread tạo `LogMessage` đẩy vào `LinkedBlockingQueue`. 
- Một `AsyncLogWriter` chạy ngầm (Background Thread) liên tục `take()` từ Queue và dùng `FileWriter` ghi nối tiếp vào các file Markdown (`.md`) ở thư mục `D:/test_results`.

## 4. Cấu Trúc File & Package (Dự Kiến)
```text
com.zcomini.backend.testify
 ┣ 📂 config          # SpeedTestConfig (HTTP/2, Virtual Threads), SseConfig
 ┣ 📂 controller      # TestRuleController (CRUD DB), PermutationTestController (SSE)
 ┣ 📂 dto             # TestLogDTO, TestCaseWrapper, RuleRequestDTO
 ┣ 📂 entity          # TestRule (Map với DB)
 ┣ 📂 repository      # TestRuleRepository
 ┣ 📂 service
 ┃ ┣ 📂 engine        # DataGeneratorEngine, ValidationMatrixEngine
 ┃ ┣ 📜 PermutationTestService # Core điều phối
 ┃ ┗ 📜 AsyncLogWriterService  # Worker ghi file .md
 ┗ 📂 utils           # Các hàm phụ trợ
```
