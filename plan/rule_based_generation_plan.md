# Kế hoạch Triển khai Tính năng: Rule-based Test Data Generation

## 1. Mục tiêu
Thay vì tạo ra các hoán vị (permutations) dựa trên giá trị cố định, hệ thống sẽ tự động sinh ra các kịch bản kiểm thử (Test Scenarios) bao gồm các trường hợp hợp lệ (Happy Path) và không hợp lệ (Negative Cases) dựa trên một bộ quy tắc ràng buộc (Validation Rules/Config).

## 2. Thiết kế luồng xử lý (Architecture Flow)
1. **Quản lý Rule Config:** Người dùng định nghĩa các tập luật (Rules) cho từng field của một API. Tập luật này được lưu trữ thành một "Config" có mã định danh (`configCode`).
2. **Kích hoạt Test (Test Runner):** Test Runner gửi một request với `configCode` (cùng URL API đích).
3. **Data Generator Engine:**
   - Đọc Config tương ứng từ Database.
   - Quét từng field, thu thập các Validation Rules.
   - Sinh ra **1 kịch bản Happy Case** (Thỏa mãn tất cả các rules).
   - Sinh ra **N kịch bản Negative Cases** (Mỗi kịch bản vi phạm 1 rule cụ thể, các rules khác vẫn hợp lệ).
4. **Executor:** Gửi toàn bộ danh sách payloads (kịch bản) vừa tạo đến API đích.
5. **Report:** So sánh HTTP Status Code / Response với kỳ vọng (VD: Happy case phải trả về 2xx, Negative cases phải trả về 400 Bad Request) để tính Pass/Fail.

## 3. Cấu trúc JSON của Validation Rule Config (Đề xuất)
```json
{
  "configCode": "USR_REG_01",
  "name": "Luật đăng ký User",
  "fields": {
    "username": {
      "type": "string",
      "required": true,
      "minLength": 5,
      "maxLength": 50,
      "pattern": "^[a-zA-Z0-9_]+$"
    },
    "email": {
      "type": "string",
      "required": true,
      "format": "email"
    },
    "age": {
      "type": "integer",
      "required": false,
      "min": 18,
      "max": 100
    },
    "status": {
      "type": "string",
      "enum": ["ACTIVE", "INACTIVE"]
    }
  }
}
```

## 4. Các câu hỏi cần chốt (Open Questions)
- Bạn muốn Rule Config này được lưu trong một bảng (Table) riêng (ví dụ `rule_configs`) hay lưu chung vào bảng `api_configs` hiện có dưới dạng một field JSON phụ?
- Logic sinh dữ liệu (Generator Engine) này sẽ nằm ở Frontend (chạy trên máy client) hay Backend? (Đề xuất nên đặt ở Backend Java để có thể tận dụng các thư viện Data Faker mạnh mẽ và dễ xử lý các luồng chạy ngầm số lượng lớn).
- Ở các Negative Cases, chúng ta có cần cấu hình cụ thể *Expected HTTP Status Code* (Ví dụ: 400 Bad Request) cho từng lỗi không, hay mặc định cứ khác status của thành công (2xx) là Pass test case báo lỗi đó?
