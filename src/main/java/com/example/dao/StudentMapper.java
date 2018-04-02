package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.model.*;

@Mapper
public interface StudentMapper
{
    @Select("select * from mahasiswa where npm = #{npm}")
    StudentModel selectStudent (@Param("npm") String npm);

    @Select("select * from mahasiswa")
    List<StudentModel> selectAllStudents ();

    @Select("select * from program_studi")
    List<StudyProgramModel> selectAllStudyProgram ();

    @Select("select * from fakultas")
    List<FacultyModel> selectAllFaculties ();

    @Select("select * from universitas")
    List<UniversityModel> selectAllUniversities ();

    @Select("select * from fakultas where id_univ=#{id_univ}")
    List<FacultyModel> selectFacultyByUniversity(@Param("id_univ") int id_univ);

    @Select("select * from program_studi where id_fakultas=#{id_fakultas}")
    List<StudyProgramModel> selectStudyProgramByFaculty(@Param("id_fakultas") int id_fakultas);

    @Select("select * from mahasiswa as m left outer join program_studi as n on m.id_prodi = n.id " +
            "left outer join fakultas as o on n.id_fakultas = o.id " +
            "left outer join universitas as p on o.id_univ = p.id " +
            "where n.id= #{prodi} and o.id = #{fakultas} and p.id = #{univ}")
    List<StudentDataTableModel> selectStudentDataTable(@Param("univ") int univ,
                                                       @Param("fakultas") int fakultas,
                                                       @Param("prodi") int prodi);

    @Insert("INSERT INTO mahasiswa (npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, status, tahun_masuk, jalur_masuk, id_prodi) VALUES (#{npm}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{agama}, #{golongan_darah}, #{status}, #{tahun_masuk}, #{jalur_masuk}, #{id_prodi})")
    void addStudent (StudentModel student);

    @Delete("DELETE FROM mahasiswa where npm = #{npm}")
    void deleteStudent(@Param("npm") String npm);

    @Update("UPDATE mahasiswa SET npm=#{npm_baru}, nama=#{nama},tempat_lahir=#{tempat_lahir},tanggal_lahir=#{tanggal_lahir}," +
            "jenis_kelamin=#{jenis_kelamin},agama=#{agama},golongan_darah=#{golongan_darah},status=#{status}," +
            "tahun_masuk=#{tahun_masuk},jalur_masuk=#{jalur_masuk},id_prodi=#{id_prodi} WHERE npm=#{npm_lama}")
    void updateStudent( @Param("npm_lama") String npm_lama,
                        @Param("npm_baru") String npm_baru,
                        @Param("nama") String nama,
                        @Param("tempat_lahir") String tempat_lahir,
                        @Param("tanggal_lahir") String tanggal_lahir,
                        @Param("jenis_kelamin") int jenis_kelamin,
                        @Param("agama") String agama,
                        @Param("golongan_darah") String golongan_darah,
                        @Param("status") String status,
                        @Param("tahun_masuk") String tahun_masuk,
                        @Param("jalur_masuk") String jalur_masuk,
                        @Param("id_prodi") int id_prodi);


    @Select("select * from program_studi where id= #{id}")
    StudyProgramModel selectStudyProgram (@Param("id") int id);

    @Select("select * from fakultas where id= #{id}")
    FacultyModel selectFaculty (@Param("id") int id);

    @Select("select * from universitas where id= #{id}")
    UniversityModel selectUniversity (@Param("id") int id);

    @Select("SELECT substring(npm,10,3) FROM `mahasiswa` " +
            "where npm like #{npm} " +
            "order by substring(npm,10,3) desc " +
            "limit 1")
    Integer getNpm(@Param("npm") String npm);

    @Select("select count(*) from mahasiswa where tahun_masuk=#{thn} and id_prodi= #{prodi} and status='Lulus'")
    Integer selectGraduateStudent(@Param("thn") String thn, @Param("prodi") int prodi);

    @Select("select count(*) from mahasiswa where tahun_masuk=#{thn} and id_prodi= #{prodi}")
    Integer selectStudentByYear(@Param("thn") String thn, @Param("prodi") int prodi);

    @Select("CALL sing_paling(#{thn},#{prodi})")
    List<StudentOldestAndYoungest> selectOldestAndYoungestStudent(@Param("thn") String thn, @Param("prodi") String prodi);

    @Update("Update mahasiswa set status=#{status} where npm=#{npm}")
    void updateStatus(@Param("status") String status, @Param("npm") String npm);
}
