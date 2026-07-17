# Danh Sách Công Việc Backend Cực Kỳ Chi Tiết (To-Do List)

- [ ] **Giai đoạn 1: Khởi tạo DB, Shared Kernel & Config**
  - [ ] 1.1 Khai báo Dependency (PostgreSQL/MySQL, Spring Data JPA, Jackson).
  - [ ] 1.2 Import / config liên kết tới project `shared-kernel` (Sử dụng `ResourceNotFoundException`, `BaseEntity`, v.v.).
  - [ ] 1.3 Thiết lập `application.yml` cho Database connection.
  - [ ] 1.4 Tạo class `SpeedTestConfig`: Cấu hình Spring Bean `HttpClient` sử dụng `Version.HTTP_2`, `ConnectTimeout(3s)`, và `VirtualThreadPerTaskExecutor`.
  - [ ] 1.5 Tạo Bean `LinkedBlockingQueue<LogMessage>(10000)` để làm Ring Buffer/Queue ghi log.

- [ ] **Giai đoạn 2: Entities, Repositories & DTOs**
  - [ ] 2.1 Entity `TestRule`: Các trường `id`, `rule_code`, `name`, `payload_schema` (Map với JSON column hoặc String length lớn).
  - [ ] 2.2 `TestRuleRepository`: Kế thừa `JpaRepository`, thêm hàm `findByRuleCode(String ruleCode)`.
  - [ ] 2.3 `TestCaseWrapper`: Class bọc 1 test case chứa (Payload Map, ExpectedStatus, Description).
  - [ ] 2.4 `TestLogDTO`: Đối tượng sẽ ném qua SSE (chứa caseId, URL, method, expectStatus, actualStatus, result, message).

- [ ] **Giai đoạn 3: Core Engines (Trái tim của hệ thống)**
  - [ ] 3.1 `DataGeneratorEngine`: Viết logic phân tích `generators` trong JSON. Triển khai các thuật toán sinh dữ liệu động `prefix_counter` (sử dụng `AtomicInteger`), `timestamp_email`, `timestamp_numeric`.
  - [ ] 3.2 `ValidationMatrixEngine` (Bước 1 - Sinh Cấu Trúc): Thuật toán bitwise Tập Lũy Thừa sinh ma trận thiếu trường. Viết logic so sánh với `required_fields` để gán cứng Expect Status = 400 nếu thiếu.
  - [ ] 3.3 `ValidationMatrixEngine` (Bước 2 - Nhúng Constraint): Duyệt JSON `constraints` cho 15+ quy tắc (String length, Enum, Unique, Regex). Nhúng `invalid_values` và `duplicate_value` vào Payload gốc để ép sinh lỗi 400, 422, 409.

- [ ] **Giai đoạn 4: Đa Luồng (Virtual Threads) & Execution**
  - [ ] 4.1 Triển khai `PermutationTestService.executeTestMatrix()`.
  - [ ] 4.2 Viết logic lặp đẻ nhánh các task vào `Executors.newVirtualThreadPerTaskExecutor()`.
  - [ ] 4.3 `runAndAssertTestCase()`: Dùng `HttpRequest.Builder` chuyển Payload thành JSON String, gửi đi. Bắt kết quả response.
  - [ ] 4.4 Logic Assertion: So sánh `response.statusCode()` với `ExpectedStatus`. Ra được cờ `PASSED / FAILED`.
  - [ ] 4.5 Bắt lỗi Exception cấp thấp (Network Timeout, 500 Internal Error) để luồng không bị crash toàn bộ hệ thống.

- [ ] **Giai đoạn 5: Real-time SSE & Async Logger**
  - [ ] 5.1 Push Event: Trong `runAndAssertTestCase()`, đóng gói `TestLogDTO` và dùng `emitter.send()` bắn về client.
  - [ ] 5.2 Xây dựng `AsyncLogWriterService`: Dùng `@PostConstruct` khởi tạo 1 Thread chạy ngầm vĩnh viễn (`while(true)`). 
  - [ ] 5.3 Lấy `LogMessage` từ `BlockingQueue` và dùng `FileWriter` ghi dữ liệu thành format Markdown `# Case_XXX.md` vào ổ D (`D:/test_results`).

- [ ] **Giai đoạn 6: Controllers (Endpoints)**
  - [ ] 6.1 `TestRuleController`: Viết CRUD cho bảng `test_rules` (để FE có thể đẩy template 15+ trường lên lưu vào DB).
  - [ ] 6.2 `PermutationTestController`: Mở API `POST /api/v1/permutations/run-stream`. 
  - [ ] 6.3 Xử lý vòng đời `SseEmitter` (set timeout 10 phút, handle ngắt kết nối `onCompletion`, `onError`).

- [ ] **Giai đoạn 7: Document & Bàn giao**
  - [ ] 7.1 Cập nhật `walkthrough.md` tổng kết luồng chạy thực tế.
  - [ ] 7.2 Postman Collection để test API tạo Rule và API chạy Test.
