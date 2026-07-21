# Kế hoạch Triển khai Kỹ thuật (Implementation Plan) - Backend `testify_core`

**Giai đoạn:** Phase 1 - Project & Folder Management
**Nguồn phân tích:** Dựa trên BA Document (Phần 2: Yêu cầu & Phần 3: Thiết kế Hệ thống)

---

## 1. Database & Data Migration (Tham chiếu: `3.1_data_dictionary` & `3.2_data_mapping`)

- **Công cụ:** Flyway hoặc Liquibase (Tùy cấu hình hiện tại của dự án).
- **Script V1 (Tạo bảng mới):**
  - Bảng `projects`: `id` (UUID), `name`, `description`, `created_at`, `updated_at`.
  - Bảng `folders`: `id`, `project_id`, `parent_folder_id`, `name`, `depth_level` (Ràng buộc: 1, 2, 3), `created_at`, `updated_at`.
- **Script V2 (Migration Dữ liệu Cũ):**
  - Thêm cột `project_id`, `folder_id` vào `rule_configs` (Chưa set NOT NULL).
  - Khởi tạo 1 bản ghi "Default Project".
  - Chạy `UPDATE` gán `project_id` của toàn bộ Rule hiện tại về "Default Project".
  - Thêm Constraint `NOT NULL` và `FOREIGN KEY`.
  - Đánh Index trên `project_id` và `parent_folder_id` để chống chậm query.

## 2. Tầng Data Access (Domain Entities & Repositories)

- **Entities:**
  - `Project.java`: Khai báo `@Entity`, `@OneToMany` tới Folder và Rule.
  - `Folder.java`: Khai báo cấu trúc Adjacency List (`@ManyToOne` tới chính nó qua `parentFolderId`).
  - Cập nhật `RuleConfig.java` thêm `@ManyToOne` trỏ về Project/Folder.
- **Repositories (Tránh N+1 - Tham chiếu `2.3_nfrd`):**
  - `ProjectRepository extends JpaRepository`.
  - `FolderRepository`: Cần hàm `findByProjectId(UUID projectId)` (Trả về một List phẳng để Frontend tự dựng cây `O(N)`).

## 3. Tầng Business Logic (Services)

Áp dụng SOLID, tách biệt `ProjectService` và `FolderService`.

- **ProjectService:**
  - `createProject(CreateProjectRequest)`
  - `deleteProject(UUID)`: **Phải chứa logic Safe Delete (Tham chiếu `3.3_uml`).** Kiểm tra `folderRepository.countByProjectId()` và `ruleConfigRepository.countByProjectId()`. Nếu `> 0`, throw `DataIntegrityViolationException`.
- **FolderService:**
  - `createFolder(CreateFolderRequest)`: **Phải chứa logic Max Depth (Tham chiếu `2.5_use_case`).** Lấy thông tin Parent Folder, nếu `parent.getDepthLevel() >= 3`, throw `BadRequestException`.
  - `deleteFolder(UUID)`: Tương tự, gọi hàm đếm để chặn xóa nếu có chứa con.

## 4. Tầng Giao tiếp (Controllers - Tham chiếu `2.4_srs`)

- **ProjectController:**
  - `GET /api/v1/projects`
  - `POST /api/v1/projects`
  - `DELETE /api/v1/projects/{id}`
- **FolderController:**
  - `GET /api/v1/projects/{projectId}/folders` (Trả Flat array List<FolderDto>).
  - `POST /api/v1/projects/{projectId}/folders`
  - `DELETE /api/v1/folders/{id}`
- **GlobalExceptionHandler:** Bắt các Custom Exception để trả về đúng `HTTP 409 Conflict` (cho Safe Delete) và `HTTP 400 Bad Request` (Max Depth).

## 5. Bảo mật (Security - Tham chiếu `2.3_nfrd`)

- Bổ sung logic kiểm tra quyền sở hữu (IDOR Check).
- Khi Client gọi `DELETE /api/v1/projects/{id}`, backend lấy ID của user từ SecurityContext (Token JWT) và query xem Project này có đúng thuộc về Tenant/User đó không trước khi tiến hành xóa.
