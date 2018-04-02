package com.example.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDataTableModel {
    private int id;
    private String npm;
    private String nama;
    private String nama_prodi;
    private String tahun_masuk;
    private String jalur_masuk;
}
