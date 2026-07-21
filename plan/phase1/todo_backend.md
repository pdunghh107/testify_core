# Danh sách Tác vụ Backend (To-Do List) - `testify_core`

**Giai đoạn:** Phase 1 - Project & Folder Management
_(Đội ngũ Backend Dev tích dấu `[x]` khi hoàn thành từng bước)_

## Phase 1.1: Database & Entities

- [ ] 1. Viết script Migration (Flyway/Liquibase) khởi tạo bảng `projects` và `folders`.
- [ ] 2. Viết script Migration (Update) thêm cột `project_id`, `folder_id` vào các bảng dữ liệu cũ (Rule, API).
- [ ] 3. Viết script Data Migration tạo "Default Project" và Update dữ liệu cũ.
- [ ] 4. Tạo class `Project.java` (JPA Entity).
- [ ] 5. Tạo class `Folder.java` (JPA Entity).
- [ ] 6. Cập nhật các Entity cũ (`RuleConfig.java`, v.v.).

## Phase 1.2: Repositories & Data Access

- [ ] 7. Tạo `ProjectRepository` interface.
- [ ] 8. Tạo `FolderRepository` interface, viết các hàm custom query:
  - `List<Folder> findByProjectId(UUID projectId)`
  - `long countByProjectId(UUID projectId)`
  - `long countByParentFolderId(UUID parentId)`

## Phase 1.3: DTOs & Validation

- [ ] 9. Tạo `ProjectDto`, `CreateProjectRequest`.
- [ ] 10. Tạo `FolderDto`, `CreateFolderRequest`.
- [ ] 11. Gắn các annotation Hibernate Validation (`@NotNull`, `@Size`...) để validate dữ liệu đầu vào.

## Phase 1.4: Business Services

- [ ] 12. Viết `ProjectService.java` -> Hàm `createProject`.
- [ ] 13. Viết `ProjectService.java` -> Hàm `deleteProject` (Bao gồm Unit Test bắt buộc cho luồng chặn xóa - Safe Delete).
- [ ] 14. Viết `FolderService.java` -> Hàm `createFolder` (Bao gồm logic tính toán `depth_level` và chặn lỗi Max Depth = 3).
- [ ] 15. Viết `FolderService.java` -> Hàm `getFoldersByProject`.
- [ ] 16. Viết `FolderService.java` -> Hàm `deleteFolder`.

## Phase 1.5: REST Controllers & Exception Handling

- [ ] 17. Viết `ProjectController.java` map các API Endpoints.
- [ ] 18. Viết `FolderController.java` map các API Endpoints.
- [ ] 19. Bổ sung cấu hình `GlobalExceptionHandler` (bắt `DataIntegrityViolationException` ép về HTTP 409).
- [ ] 20. Bổ sung lớp Security Interceptor / AOP kiểm tra quyền (Chống IDOR khi truyền param `id`).

## Phase 1.6: Review & Refactoring

- [ ] 21. Kiểm tra lại code tuân thủ DRY: Có thể gom chung logic check quyền sở hữu (Ownership) vào một Component/Util dùng chung.
- [ ] 22. Tự test qua Postman (hoặc Swagger) để xác nhận Response time < 200ms.
- [ ] 23. Commit code và bàn giao cho Frontend/QA.
