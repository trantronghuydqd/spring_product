# Hướng dẫn cài đặt và sử dụng Chat AI với Ollama

## Bước 1: Cài đặt Ollama

### Windows:

1. Tải Ollama từ: https://ollama.com/download
2. Chạy file installer và cài đặt
3. Ollama sẽ tự động chạy như service

### Kiểm tra Ollama đã cài đặt:

```powershell
ollama --version
```

## Bước 2: Pull model AI

### Pull model llama3.2 (khuyến nghị - nhẹ, nhanh):

```powershell
ollama pull llama3.2
```

### Hoặc pull model mistral (tốt cho tiếng Việt):

```powershell
ollama pull mistral
```

### Kiểm tra models đã có:

```powershell
ollama list
```

## Bước 3: Chạy ứng dụng

1. Maven sẽ tự động tải Spring AI dependencies
2. Chạy ứng dụng:

```powershell
mvn spring-boot:run
```

3. Truy cập: http://localhost:8080/chat

## Bước 4: Sử dụng

1. Vào trang Chat AI từ menu navbar
2. Nhập câu hỏi, ví dụ:
    - "Laptop nào tốt cho sinh viên?"
    - "iPhone 15 giá bao nhiêu?"
    - "Tư vấn tai nghe bluetooth"
3. AI sẽ trả lời dựa trên danh sách sản phẩm trong database

## Troubleshooting

### Lỗi: Connection refused

-   Kiểm tra Ollama đang chạy: `ollama serve`
-   Kiểm tra port 11434: `netstat -ano | findstr :11434`

### Lỗi: Model not found

-   Pull model: `ollama pull llama3.2`

### Thay đổi model trong application.properties:

```properties
spring.ai.ollama.chat.options.model=mistral
# hoặc
spring.ai.ollama.chat.options.model=llama3.2
```

## Cấu hình nâng cao

### Temperature (độ sáng tạo):

-   0.0-0.3: Chính xác, ít sáng tạo
-   0.4-0.7: Cân bằng (khuyến nghị)
-   0.8-1.0: Sáng tạo, đa dạng

```properties
spring.ai.ollama.chat.options.temperature=0.7
```

## Models khác có thể dùng:

-   `llama3.2` - Nhẹ, nhanh, tốt (1.3GB)
-   `mistral` - Tốt cho tiếng Việt (4.1GB)
-   `gemma2` - Google Gemma (5.4GB)
-   `qwen2.5` - Alibaba Qwen (4.7GB)

Pull model mới:

```powershell
ollama pull <model-name>
```

## Tài nguyên

-   Ollama: https://ollama.com
-   Spring AI: https://docs.spring.io/spring-ai/reference/
-   Models library: https://ollama.com/library
