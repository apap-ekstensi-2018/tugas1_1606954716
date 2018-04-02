package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.StudentMapper;
import com.example.model.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceDatabase implements StudentService
{
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentModel selectStudent (String npm)
    {
        log.info ("select student with npm {}", npm);
        return studentMapper.selectStudent (npm);
    }
    @Override
    public StudyProgramModel selectStudyProgram (int id)
    {
        log.info ("select student with npm {}", id);
        return studentMapper.selectStudyProgram (id);
    }

    @Override
    public FacultyModel selectFaculty (int id)
    {
        log.info ("select student with fakultas {}", id);
        return studentMapper.selectFaculty (id);
    }

    @Override
    public UniversityModel selectUniversity (int id)
    {
        log.info ("select student with fakultas {}", id);
        return studentMapper.selectUniversity (id);
    }

    @Override
    public List<StudentModel> selectAllStudents ()
    {
        log.info ("select all students");
        return studentMapper.selectAllStudents ();
    }

    @Override
    public List<StudyProgramModel> selectAllStudyProgram ()
    {
        log.info ("select all students");
        return studentMapper.selectAllStudyProgram();
    }

    @Override
    public List<FacultyModel> selectAllFaculties()
    {
        log.info("select all universities");
        return studentMapper.selectAllFaculties();
    }

    @Override
    public List<UniversityModel> selectAllUniversities()
    {
        log.info("select all universities");
        return studentMapper.selectAllUniversities();
    }

    @Override
    public Integer selectGraduateStudent(String thn, int prodi)
    {
        log.info("select graduate student");
        return studentMapper.selectGraduateStudent(thn, prodi);
    }

    @Override
    public Integer selectStudentByYear(String thn, int prodi)
    {
        log.info("select graduate student");
        return studentMapper.selectStudentByYear(thn, prodi);
    }

    @Override
    public List<FacultyModel> selectFacultyByUniversity(int id_univ)
    {
        return studentMapper.selectFacultyByUniversity(id_univ);
    }

    @Override
    public List<StudyProgramModel> selectStudyProgramByFaculty(int id_fakultas)
    {
        return studentMapper.selectStudyProgramByFaculty(id_fakultas);
    }

    @Override
    public List<StudentDataTableModel> selectStudentDataTable(int univ, int fakultas, int prodi)
    {
        return studentMapper.selectStudentDataTable(univ, fakultas, prodi);
    }

    @Override
    public void addStudent (StudentModel student)
    {
        studentMapper.addStudent (student);
    }

    @Override
    public void deleteStudent (String npm)
    {
        log.info("student " + npm + " deleted");
        studentMapper.deleteStudent(npm);
    }

    @Override
    public void updateStudent( String npm_lama, String npm_baru,String nama,
                               String tempat_lahir,String tanggal_lahir,
                               int jenis_kelamin,String agama,
                               String golongan_darah,
                               String status,
                               String tahun_masuk,
                               String jalur_masuk,
                               int id_prodi)
    {
        log.info("Student " + npm_baru + "updated");
        studentMapper.updateStudent( npm_lama, npm_baru, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, status, tahun_masuk, jalur_masuk, id_prodi);
    }

    @Override
    public Integer getNpm(String npm)
    {
        return studentMapper.getNpm(npm);
    }

    @Override
    public List<StudentOldestAndYoungest> selectOldestAndYoungestStudent(String thn, String prodi)
    {
        return studentMapper.selectOldestAndYoungestStudent(thn, prodi);
    }

    @Override
    public void updateStatus (String status,String npm)
    {
        studentMapper.updateStatus(status,npm);
    }
}
