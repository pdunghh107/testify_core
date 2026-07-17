# Hướng Dẫn Sử Dụng & API Walkthrough - Testify Core

## 1. Tổng Quan Hệ Thống (System Overview)
Testify Core là một Engine chuyên dụng dành riêng cho Kiểm thử API tự động (API Test Automation). Module `testify-service` được xây dựng bằng kiến trúc:
- **Đa luồng hiệu năng cao**: Tận dụng `Virtual Threads` (Java 21) không giới hạn.
- **Log bất đồng bộ**: Dữ liệu sau khi kiểm thử được đẩy vào `LinkedBlockingQueue` và một Worker chạy nền sẽ ghi ra file ở thư mục `D:/test_results` liên tục mà không gây gián đoạn luồng test mạng.
- **Real-time SSE**: Gửi kết quả `TestLogDTO` thẳng về React Frontend theo dạng Event Stream, không cần chờ toàn bộ vòng lặp kết thúc.

## 2. Luồng Chạy Thực Tế (Execution Flow)
1. **Quản trị Rule (JSON Schema)**: Bạn sử dụng API `/api/v1/rules` để đẩy toàn bộ cấu hình 15+ trường (generators, constraints, required_fields) vào bảng `test_rules` ở Database (PostgreSQL).
2. **Khởi Chạy Test Stream**: Giao diện (React) gọi POST `/api/v1/permutations/run-stream`. Server trả về đối tượng `SseEmitter`.
3. **Sinh Hoán Vị (Matrix Engine)**: Payload mẫu sẽ được kết hợp với JSON Config từ DB, đẻ ra hàng nghìn Test Cases:
   - Thiếu trường (`missing fields`)
   - Regex/Enum sai (`invalid values`)
   - Dữ liệu trùng lặp (`duplicate_value`)
4. **Bắn API song song**: Cả nghìn trường hợp được quăng vào Virtual Thread Pool gọi tới API đích trong nháy mắt.

## 3. Danh Sách API (Postman Walkthrough)

### 3.1. API Lưu Cấu Hình Rule
**POST /api/v1/rules**
```json
{
  "ruleCode": "staff_creation_api",
  "name": "API Tạo Nhân Viên",
  "payloadSchema": "{\"required_fields\":[\"staff_code\",\"email\"],\"generators\":{\"staff_code\":{\"type\":\"prefix_counter\",\"prefix\":\"STF_\"}}}"
}
```

### 3.2. API Chạy Test & Stream Log (Real-time SSE)
**POST /api/v1/permutations/run-stream**
```json
{
  "url": "https://api.duannoibo.com/staffs",
  "method": "POST",
  "rule_code": "staff_creation_api",
  "payload": {
    "staff_code": "",
    "email": ""
  },
  "required_fields": ["staff_code", "email"],
  "generators": {
    "staff_code": { "type": "prefix_counter", "prefix": "STF_" }
  },
  "constraints": {
    "email": {
      "type": "unique",
      "duplicate_value": "existing_email@company.com"
    }
  }
}
```

*(Lưu ý: Payload JSON trên là demo trực tiếp. Nếu bạn gọi API `GET /api/v1/rules/staff_creation_api` để lấy logic từ DB thì có thể tự động nhúng vào body phía Frontend).*

## 4. Xác Nhận Ghi Log (Disk Write Check)
Sau khi kết thúc, truy cập thư mục `D:/test_results`. Bạn sẽ thấy hàng loạt file Markdown `Case_001.md`, `Case_002.md`, v.v. được sinh ra với nội dung chi tiết payload gửi đi và response bắt được.
