# 1. Pakai bahan dasar Java 17 (versi ringan/Alpine)
FROM amazoncorretto:17-alpine-jdk

# 2. Buat folder kerja di dalam container
WORKDIR /app

# 3. Copy hasil build jar dari laptop ke dalam container
# Pastikan nama file .jar nya sesuai dengan yang ada di folder target nanti
COPY target/*.jar app.jar

# 4. Expose port 8080 (Pintu akses)
EXPOSE 8080

# 5. Perintah untuk menyalakan aplikasi saat container jalan
ENTRYPOINT ["java","-jar","app.jar"]