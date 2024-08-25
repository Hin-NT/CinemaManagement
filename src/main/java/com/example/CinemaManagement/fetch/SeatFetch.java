package com.example.CinemaManagement.fetch;

import com.example.CinemaManagement.entity.Seat;
import com.example.CinemaManagement.entity.Theater;
import com.example.CinemaManagement.enums.SeatStatus;
import com.example.CinemaManagement.enums.SeatType;
import okhttp3.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeatFetch {

    public static void main(String[] args) {
        String filePath = "path/to/your/file.xlsx"; // Đường dẫn tới file Excel
        List<Seat> seats = readExcelFile(filePath); // Đọc dữ liệu từ Excel

        // Gọi API cho từng seat
        assert seats != null;
        for (Seat seat : seats) {
            sendSeatData(seat);
        }
    }

    public static List<Seat> readExcelFile(String filePath) {
        List<Seat> seats = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Đọc sheet đầu tiên

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ qua hàng đầu tiên (tiêu đề)
                Row row = sheet.getRow(i);
                if (row != null) {
                    String seatId = row.getCell(0).getStringCellValue();
                    SeatType seatType = SeatType.values()[(int) row.getCell(1).getNumericCellValue()];
                    SeatStatus seatStatus = SeatStatus.values()[(int) row.getCell(2).getNumericCellValue()];

                    // Assuming you have a method to fetch Theater by ID or create dummy Theater object
                    List<Theater> theaters = new ArrayList<>();
                    // Add Theater objects to the list based on your logic
                    Theater theater = new Theater();
                    theater.setTheaterId((int) row.getCell(3).getNumericCellValue());
                    theaters.add(theater);

                    // Create Seat object and add to the list
                    Seat seat = new Seat(seatId);
                    seats.add(seat);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return seats;
    }

    public static void sendSeatData(Seat seat) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        // Định nghĩa MediaType cho JSON
        MediaType mediaType = MediaType.parse("application/json");

        // Tạo nội dung JSON cho yêu cầu
//        String jsonBody = seat.toJson(); // Sử dụng phương thức toJson() để lấy định dạng JSON
        String jsonBody = "";
        RequestBody body = RequestBody.create(jsonBody, mediaType);

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/seat")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        // Gửi yêu cầu và xử lý phản hồi
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Xử lý phản hồi
            System.out.println("Response for seat " + seat.getSeatId() + ": " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
    }
}
