package com.example.service;

import java.util.List;

import com.example.model.*;

public interface StudentService
{
    StudentModel selectStudent (String npm);

    StudyProgramModel selectStudyProgram(int id);

    FacultyModel selectFaculty(int id);

    UniversityModel selectUniversity(int id);

    List<StudentModel> selectAllStudents ();

    List<UniversityModel> selectAllUniversities();

    List<FacultyModel> selectAllFaculties();

    List<StudyProgramModel> selectAllStudyProgram();

    List<FacultyModel> selectFacultyByUniversity(int id_univ);

    List<StudyProgramModel> selectStudyProgramByFaculty(int id_fakultas);

    List<StudentDataTableModel> selectStudentDataTable(int univ, int fakultas, int prodi);

    Integer selectGraduateStudent(String thn, int prodi);

    Integer selectStudentByYear(String thn, int prodi);

    void addStudent (StudentModel student);

    void deleteStudent (String npm);

    void updateStudent(String npm_lama, String npm_baru,String nama,String tempat_lahir,String tanggal_lahir,int jenis_kelamin,String agama,String golongan_darah,String status,String tahun_masuk,String jalur_masuk,int id_prodi);

    Integer getNpm(String npm);

    List<StudentOldestAndYoungest> selectOldestAndYoungestStudent(String thn, String prodi);

    void updateStatus(String status, String npm);
}
