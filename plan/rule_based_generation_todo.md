# Todo List: Rule-based Test Data Generation

## Phase 1: Lưu trữ và Quản lý Rule Config (Backend & Frontend)
- [x] Quyết định và thiết kế Database schema (Cập nhật Entity `ApiConfig` hoặc tạo Entity `RuleConfig` mới).
- [x] Viết API CRUD ở Backend để lưu trữ và quản lý Rule Config.
- [x] Tạo giao diện Frontend (Rule Builder UI) cho phép người dùng kéo thả/chọn các Validation Rules (Required, Min, Max, Email, Regex...) cho từng field.

## Phase 2: Xây dựng Data Generator Engine (Backend)
- [x] Xây dựng bộ Engine Parse file JSON chứa Rules.
- [x] Xây dựng thuật toán sinh Happy Case (Sử dụng library như DataFaker để tạo dữ liệu ngẫu nhiên hợp lệ).
- [x] Xây dựng thuật toán sinh Negative Cases (Duyệt qua từng field, ứng với mỗi constraint/rule sẽ tạo ra một bản sao payload vi phạm duy nhất constraint đó - các fields còn lại vẫn dùng dữ liệu hợp lệ).
- [ ] Viết Unit Test cho Generator Engine để đảm bảo tính đúng đắn của các payload sinh ra.

## Phase 3: Tích hợp vào Test Runner
- [x] Cập nhật API nhận request của Test Runner để nhận thêm param `ruleConfigCode`.
- [x] Cập nhật logic Executor: Nhận `ruleConfigCode` -> Gọi Generator Engine đẻ ra danh sách danh sách payloads -> Chạy vòng lặp bắn request.
- [x] Thu thập kết quả response (Body, Status Code, Time) của toàn bộ tập payload và tổng hợp thành đối tượng Report.

## Phase 4: Nâng cấp UI Test Runner & Báo cáo
- [x] Thêm tính năng chọn Rule Config từ Dropdown ở màn hình Test Runner (thay vì nhập payload tay).
- [x] Thiết kế lại màn hình Test Report để hiển thị chi tiết theo nhóm: Happy Case (Pass/Fail), Negative Cases (Thiếu field A, Sai format B...) kèm Status tương ứng.
