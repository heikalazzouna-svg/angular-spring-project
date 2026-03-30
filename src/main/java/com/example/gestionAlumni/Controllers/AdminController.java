package com.example.gestionAlumni.Controllers;

import com.example.gestionAlumni.DTO.AlumniAdminSummaryDTO;
import com.example.gestionAlumni.DTO.StudentAdminSummaryDTO;
import com.example.gestionAlumni.Entities.Administrator;
import com.example.gestionAlumni.Entities.Alumni;
import com.example.gestionAlumni.Services.AdminService;
import com.example.gestionAlumni.Services.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Administrator admin = adminService.authenticate(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/alumni/pending")
    public ResponseEntity<List<AlumniAdminSummaryDTO>> getPendingAlumni() {
        return ResponseEntity.ok(adminService.listPendingAlumni());
    }

    @GetMapping("/alumni/verified")
    public ResponseEntity<List<AlumniAdminSummaryDTO>> getVerifiedAlumni() {
        return ResponseEntity.ok(adminService.listVerifiedAlumni());
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentAdminSummaryDTO>> getAllStudents() {
        return ResponseEntity.ok(adminService.listAllStudents());
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<Alumni> approve(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.approveAlumni(id));
    }

    @DeleteMapping("/reject/{id}")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        adminService.rejectAlumni(id);
        return ResponseEntity.ok("Alumni rejected and notified");
    }
}